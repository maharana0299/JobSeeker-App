import 'dart:io';

import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:dropdown_search/dropdown_search.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:jobseeker_company/Widgets/eidt_widgets.dart';


class ProfilePage extends StatefulWidget {
  @override
  _ProfilePageState createState() => _ProfilePageState();
}

class _ProfilePageState extends State<ProfilePage> {
  User user = FirebaseAuth.instance.currentUser;
  final companyName = 'companyName';
  final about = 'aboutCompany';
  final address = 'address';

  DocumentReference ref = FirebaseFirestore.instance
      .collection('company')
      .doc(FirebaseAuth.instance.currentUser.uid);

  @override
  Widget build(BuildContext context) {
    return SafeArea(
        child: SingleChildScrollView(
      child: StreamBuilder<DocumentSnapshot>(
          stream: ref.snapshots(),
          builder: (context, snapshot) {
            if (snapshot.hasData) {
              // final List arr = snapshot.data['skills'];
              return Column(
                children: <Widget>[
                  Stack(
                    overflow: Overflow.visible,
                    alignment: Alignment.center,
                    children: <Widget>[
                      Container(
                        child: GestureDetector(
                            child: CircleAvatar(
                              radius: 50,
                              backgroundColor: Colors.white,
                              backgroundImage:
                                  NetworkImage(snapshot.data['photoUrl']),
                            ),
                            onDoubleTap: () {
                              print('jbjgjgjhg');
                              _changePhoto();
                            }),
                      ),
                    ],
                  ),
                  SizedBox(
                    height: 50,
                  ),
                  EditIcon(
                    message: snapshot.data['$companyName'] ?? 'Add Name',
                    onPressed: () {
                      Navigator.push(
                        context,
                        MaterialPageRoute(
                          builder: (context) => EditChanges(
                            name: snapshot.data['$companyName'],
                            field: '$companyName',
                          ),
                        ),
                      );
                    },
                  ),
                  EditIcon(
                      onPressed: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => EditChanges(
                                field: 'phone', name: snapshot.data['phone']),
                          ),
                        );
                      },
                      message:
                          '${snapshot.data['phone'] ?? 'Enter Phone Number'}'),
                  EditIcon(
                      onPressed: null,
                      showIcon: false,
                      message: 'Email',
                      subTitle: snapshot.data['email']),
                  EditIcon(
                      onPressed: () {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) => EditChanges(
                                      name: snapshot.data
                                              .data()
                                              .containsKey('$about')
                                          ? snapshot.data['$about']
                                          : '',
                                      field: '$about',
                                    )));
                      },
                      message: 'About Company',
                      subTitle: snapshot.data.data().containsKey('$about')
                          ? snapshot.data['$about'] ?? 'Add About Me'
                          : 'Add About Company'),
                  EditIcon(
                      onPressed: () {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) => EditChanges(
                                      name: snapshot.data
                                              .data()
                                              .containsKey('$address')
                                          ? snapshot.data['$address']
                                          : '',
                                      field: '$address',
                                    )));
                      },
                      message: 'Address',
                      subTitle: snapshot.data.data().containsKey('$address')
                          ? snapshot.data['$address'] ?? 'Enter Address'
                          : 'Enter Address'),
                  // buildSkillContent(snapshot, arr, context),
                  // buildDropDown(snapshot, context, 'Qualification',
                  //     qualification, 'qualification'),
                  // buildDropDown(snapshot, context, 'Work Experience',
                  //     workExperience, 'experience'),
                ],
              );
            } else {
              return Container();
            }
          }),
    ));
  }

  Column buildSkillContent(AsyncSnapshot<DocumentSnapshot> snapshot, List arr,
      BuildContext context) {
    return Column(
      children: [
        ListTile(
          contentPadding: EdgeInsets.only(left: 30, right: 30),
          title: Text('Skills'),
        ),
        Wrap(
          crossAxisAlignment: WrapCrossAlignment.start,
          alignment: WrapAlignment.start,
          spacing: 10.0,
          children: snapshot.data['skills'] != null
              ? arr
                  .map(
                    (e) => SkillTile(
                      skill: e,
                      onTappedCancel: () {
                        arr.remove(e);
                        ref.update({'skills': arr});
                      },
                    ),
                  )
                  .toList()
              : [Container()],
        ),
        ListTile(
          contentPadding: EdgeInsets.only(left: 30, right: 30),
          title: Text(
            'Add More Skills?',
            style: TextStyle(
              color: Colors.red,
              fontSize: 12,
            ),
          ),
          onTap: () {
            Navigator.push(
                context,
                MaterialPageRoute(
                    builder: (context) => EditSkills(
                          skills: snapshot.data.data().containsKey('skills')
                              ? snapshot.data['skills']
                              : [],
                          field: 'skills',
                        )));
          },
        ),
      ],
    );
  }

  ListTile buildDropDown(AsyncSnapshot<DocumentSnapshot> snapshot,
      BuildContext context, String heading, List<String> ls, String field) {
    return ListTile(
      contentPadding: EdgeInsets.only(left: 30, right: 30),
      onTap: () {},
      title: Text('$heading'),
      subtitle: DropdownSearch<String>(
        mode: Mode.MENU,
        maxHeight: 300,
        items: ls,
        label: "Menu",
        onChanged: (i) {
          ref.update({'$field': i});
        },
        selectedItem: snapshot.data['$field'] ?? 'Please Select',
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

  _changePhoto() async {
    final pick = await ImagePicker()
        .getImage(source: ImageSource.camera, imageQuality: 50);

    File file = File(pick.path);
    if (pick != null) {
      final ref = FirebaseStorage.instance
          .ref()
          .child(user.uid)
          .child('profile_image.jpeg');
      await ref.putFile(file);
      await ref.getDownloadURL().then((value) {
        FirebaseFirestore.instance.collection('company').doc(user.uid).update(
          {
            'photoUrl': value.toString(),
          },
        ).then((value) => null);
        user
            .updateProfile(
          photoURL: value.toString(),
        )
            .then((value) {
          print(user.photoURL);
          // setState(() {});
        });
      });
    }
  }

  //todo add resume option
}
