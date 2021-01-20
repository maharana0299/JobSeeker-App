import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:dropdown_search/dropdown_search.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:jobseeker_company/utils/firebase_helper.dart';

import '../constants.dart';
import '../navigation_drawer.dart';

class JobPosted extends StatefulWidget {
  static const String id = '/jobPosted';
  @override
  _JobPostedState createState() => _JobPostedState();
}

class _JobPostedState extends State<JobPosted> {

  String companyName;
  String email;
  String experience;
  bool isComplete = true;
  String qualification;
  List<String> skill;
  String type;
  String number;
  String aboutCompany;
  final postedBy = TextEditingController();
  final skills = TextEditingController();
  final salary = TextEditingController();
  final vacancies = TextEditingController();
  final title = TextEditingController();
  final about = TextEditingController();
  final duration = TextEditingController();

  final DocumentReference userReference = FirebaseFirestore.instance
      .collection('company')
      .doc(FirebaseAuth.instance.currentUser.uid);
  final CollectionReference jobReference =
      FirebaseFirestore.instance.collection('jobs');
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Post A Job'),
      ),
      drawer: NavigationDrawer(),
      body: SafeArea(
        child: SingleChildScrollView(
          child: StreamBuilder<DocumentSnapshot>(
              stream: userReference.snapshots(),
              builder: (context, snapshot) {
                if (snapshot.hasData) {
                  if (snapshot.data.data().containsValue(null)) {
                    print('yes');
                    isComplete = false;
                  }
                  companyName = snapshot.data['companyName'];
                  email = snapshot.data['email'];
                  number = snapshot.data['phone'];
                  aboutCompany = snapshot.data['aboutCompany'];

                  return Column(
                    children: [
                      ListTile(
                        tileColor: Colors.white,
                        title: Text('Company Name'),
                        subtitle: Text(
                          '${snapshot.data['companyName']}',
                          style: TextStyle(color: Colors.redAccent),
                        ),
                      ),
                      ListTile(
                        tileColor: Colors.grey,
                        title: Text('Email'),
                        subtitle: Text('${snapshot.data['email']}',
                            style: TextStyle(color: Colors.redAccent)),
                      ),
                      ListTile(
                        tileColor: Colors.white,
                        title: Text('Contact Number'),
                        subtitle: Text('${snapshot.data['phone']}',
                            style: TextStyle(color: Colors.redAccent)),
                      ),
                      ListTile(
                        tileColor: Colors.grey,
                        title: Text('About Company'),
                        subtitle: Text(
                            '${snapshot.data['aboutCompany'] ?? 'Complete Your Profile Before Posting a job'}',
                            style: TextStyle(color: Colors.redAccent)),
                      ),
                      PostJobHelperTile(
                          controller: title, title: 'Enter Job Title'),
                      PostJobHelperTile(
                        controller: about,
                        title: 'About Job',
                      ),
                      PostJobHelperTile(
                        controller: salary,
                        title: 'Salary',
                      ),
                      PostJobHelperTile(
                        controller: vacancies,
                        title: 'No of Vacancy',
                      ),
                      PostJobHelperTile(
                          controller: skills,
                          title: 'Skills Req',
                          hint: '*Enter comma separated skills'),
                      PostJobHelperTile(
                          controller: duration,
                          title: 'Duration',
                          hint: '*Enter Duration'),
                      PostJobDropDownTile(
                        heading: 'Min Qualification',
                        ls: kQualification,
                        onChange: (s) {
                          qualification = s;
                        },
                      ),
                      PostJobDropDownTile(
                        heading: 'Experience Req',
                        ls: kWorkExperience,
                        onChange: (s) {
                          experience = s;
                        },
                      ),
                      PostJobDropDownTile(
                        heading: 'Job Type',
                        ls: kJobType,
                        onChange: (s) {
                          type = s;
                        },
                      ),
                      Container(
                        margin: EdgeInsets.all(20),
                        child: RaisedButton(
                          elevation: 10.0,
                          child: Text('Apply'),
                          color: Colors.blueAccent,
                          textColor: Colors.white,
                          onPressed: () {
                            if (
                            companyName == null ||
                            email == null ||
                            number == null ||
                            aboutCompany == null ||
                            title.text.isEmpty ||
                            about.text.isEmpty ||
                            salary.text.isEmpty ||
                            vacancies.text.isEmpty ||
                            skills.text.isEmpty ||
                            qualification  == null ||
                            experience == null ||
                                duration == null ||
                            type == null) {
                              FirebaseHelper.instance.makeToast('Please Fill Correctly');
                            } else {
                              skill = skills.text.trim().replaceAll('\n','').split(',');
                              skill.removeWhere((element) {
                                return element.trim() == '' ? true : false;
                              });
                              print('$skill');

                              final map = {
                                'companyName' : companyName,
                                'email' : email,
                                'number' : number,
                                'aboutCompany' : aboutCompany,
                                'title' : title.text,
                                'about' : about.text,
                                'salary': salary.text,
                                'vacancies' : vacancies.text,
                                'skills': skill,
                                'qualification' : qualification,
                                'experience' : experience,
                                'type'  :type,
                                'createdBy' : userReference,
                                'duration' : duration.text
                              };
                              print('$map');
                              jobReference.doc().set(map)
                              .then((value){
                                FirebaseHelper.instance.makeToast('Job Posted Successfully');
                              })
                              .catchError((e){
                                FirebaseHelper.instance.makeToast(e.toString());
                              });
                            }
                          },
                        ),
                      ),
                      SizedBox(
                        height: MediaQuery.of(context).size.height / 8,
                      )
                    ],
                  );
                } else
                  return CircularProgressIndicator();
              }),
        ),
      ),
    );
  }
}

class PostJobHelperTile extends StatelessWidget {
  const PostJobHelperTile({
    Key key,
    @required this.controller,
    @required this.title,
    this.hint,
  }) : super(key: key);

  final TextEditingController controller;
  final title;
  final hint;
  @override
  Widget build(BuildContext context) {
    return ListTile(
      tileColor: Colors.white,
      title: Text('$title'),
      subtitle: TextField(
        minLines: 1,
        maxLines: 10,
        keyboardType: TextInputType.multiline,
        controller: controller,
        style: TextStyle(color: Colors.white),
        decoration: hint != null
            ? kTextFieldDecoration.copyWith(
                hintText: hint,
              )
            : kTextFieldDecoration,
      ),
    );
  }
}

class PostJobDropDownTile extends StatelessWidget {
  final String heading;
  final List<String> ls;
  final ValueChanged onChange;
  PostJobDropDownTile(
      {@required this.heading, @required this.ls, @required this.onChange});

  @override
  Widget build(BuildContext context) {
    return ListTile(
      contentPadding: EdgeInsets.only(left: 30, right: 30),
      onTap: () {},
      title: Text('$heading'),
      subtitle: DropdownSearch<String>(
        mode: Mode.MENU,
        maxHeight: 300,
        items: ls,
        label: "Menu",
        onChanged: onChange,
        onSaved: (s) {
          print(s);
        },
        selectedItem: 'Please Select',
        showSearchBox: true,
        searchBoxDecoration: InputDecoration(
          border: OutlineInputBorder(),
          contentPadding: EdgeInsets.fromLTRB(12, 12, 8, 0),
          labelText: "Select $heading",
        ),
        popupTitle: Container(
          height: MediaQuery.of(context).size.height / 10,
          decoration: BoxDecoration(
            color: Theme.of(context).accentColor,
            borderRadius: BorderRadius.only(
              topLeft: Radius.circular(20),
              topRight: Radius.circular(20),
            ),
          ),
          child: Center(
            child: Text(
              '$heading',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
                color: Colors.white,
              ),
            ),
          ),
        ),
        popupShape: RoundedRectangleBorder(
          borderRadius: BorderRadius.only(
            topLeft: Radius.circular(24),
            topRight: Radius.circular(24),
          ),
        ),
      ),
      //   Text(snapshot.data['qualification'] ??
      //       'Select A Qualification'),
    );
  }
}
