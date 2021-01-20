import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:jobseeker_company/Widgets/eidt_widgets.dart';
import 'package:rflutter_alert/rflutter_alert.dart';

class ViewUserProfile extends StatefulWidget {
  final DocumentReference ref ;
  final QueryDocumentSnapshot snap;
  final QueryDocumentSnapshot jobSnap;
  ViewUserProfile({@required this.ref, this.snap,this.jobSnap});

  @override
  _ViewUserProfileState createState() => _ViewUserProfileState();
}

class _ViewUserProfileState extends State<ViewUserProfile> {
  String status;
  @override
  Widget build(BuildContext context) {
    return StreamBuilder<DocumentSnapshot>(
        stream: widget.ref.snapshots(),
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            final data = snapshot.data.data();
            return Scaffold(
              appBar: AppBar(),
              floatingActionButton: buildApplyFloatingButton(context, widget.ref, widget.snap,widget.jobSnap),
              body: SingleChildScrollView(
                child: Column(
                  children: [
                    Stack(
                      overflow: Overflow.visible,
                      alignment: Alignment.center,
                      children: [
                        Container(
                          child: GestureDetector(
                            child: CircleAvatar(
                              radius: 50,
                              backgroundColor: Colors.white,
                              backgroundImage:
                              NetworkImage(snapshot.data['photoUrl']),
                            ),
                          ),
                        ),
                      ],
                    ),
                    EditIcon(
                      message: 'Name',
                      subTitle: data['name'],
                      showIcon: false,
                    ),
                    EditIcon(
                      message: 'Phone Number',
                      subTitle: data['phone'],
                      showIcon: false,
                    ),
                    EditIcon(
                      message: 'Email',
                      subTitle: data['email'],
                      showIcon: false,
                    ),
                    EditIcon(
                      message: 'About',
                      subTitle: data['aboutMe'],
                      showIcon: false,
                    ),
                    EditIcon(
                      message: 'Experience',
                      subTitle: data['experience'],
                      showIcon: false,
                    ),
                    EditIcon(
                      message: 'Skills',
                      subTitle: data['skills'].toString(),
                      showIcon: false,
                    ),

                    if (data['resume'] != null ) ListTile(
                      contentPadding: EdgeInsets.only(left: 30, right: 30),
                      title: Text( 'Resume'),
                      subtitle:  Text('Hello'),
                    ),
                  ],
                ),
              ),
            );
          }
          else return CircularProgressIndicator();
        }
    );
  }

  buildApplyFloatingButton(BuildContext context,
      final DocumentReference ref, final QueryDocumentSnapshot user,final QueryDocumentSnapshot jobDetail) {
    return Container(
      width: MediaQuery
          .of(context)
          .size
          .width / 3,
      child: FloatingActionButton(
        onPressed:  (){
          Alert(
            context: context,
            type: AlertType.error,
            title: "After Short listing they will be able to message you ",
            buttons: [
              DialogButton(
                child:user.data()['status']  == 'Applied' ?Text(
                  "Shortlist",
                  style: TextStyle(color: Colors.white, fontSize: 20),
                ) : Text('ShortListed'),
                onPressed:user.data()['status']  == 'Applied' ? () {
                  ref.collection('appliedJobs').doc(jobDetail.id)
                  .update({
                    'status' : 'InProcess',
                  }).then((value) => {
                    user.reference.update({
                      'status' : 'InProcess',
                    }).then((a){

                      // print(jobDetail.data().toString());
                      final DocumentReference createdByRef = jobDetail['createdBy'];
                      final companyId = createdByRef.id;
                      final userId = ref.id;
                      // print('$companyId $userId');
                      ref.collection('chattingWith').doc(companyId).set({
                        'name' : jobDetail.data()['companyName'],
                      });
                      createdByRef.collection('chattingWith').doc(userId).set({
                        'name' : user.data()['name'],
                      });
                    })
                  });
                  setState(() {
                    Navigator.pop(context);
                  });

                } : null,
              ),
              DialogButton(
                child: Text(
                  "No",
                  style: TextStyle(color: Colors.white, fontSize: 20),
                ),
                onPressed: () {
                  Navigator.pop(context);
                },
              ),
            ],
          ).show();
        },
        child:widget.snap.data()['status'] == 'Applied' ?Text('Shortlist?') : Text(widget.snap.data()['status']),
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(10)),
      ),
    );
  }
}
