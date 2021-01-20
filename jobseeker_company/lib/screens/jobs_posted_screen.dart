import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:jobseeker_company/screens/applied_people.dart';
import 'package:rflutter_alert/rflutter_alert.dart';

class JobsPosted extends StatefulWidget {
  @override
  _JobsPostedState createState() => _JobsPostedState();
}

class _JobsPostedState extends State<JobsPosted> {
  final DocumentReference userReference = FirebaseFirestore.instance
      .collection('company')
      .doc(FirebaseAuth.instance.currentUser.uid);

  @override
  Widget build(BuildContext context) {

    return SafeArea(
        child: Stack(
      children: [
        Container(
          width: MediaQuery.of(context).size.width,
          height: MediaQuery.of(context).size.height / 4,
          child: CustomPaint(
            painter: CurvePainter(),
          ),
        ),
        Column(
          children: [
            Container(
              width: MediaQuery.of(context).size.width,
              height: MediaQuery.of(context).size.height / 8,
              child: Center(
                child: Text(
                  'Jobs Posted',
                  style: TextStyle(
                      color: Colors.white,
                      fontSize: MediaQuery.of(context).size.height / 30),
                ),
              ),
            ),
            StreamBuilder<QuerySnapshot>(
                stream: FirebaseFirestore.instance
                    .collection('jobs')
                    .where('createdBy',
                        isEqualTo: userReference)
                    .snapshots(),
                builder: (bc, snapshot) {
                  if (snapshot.hasData) {
                    final List<QueryDocumentSnapshot> snap = snapshot.data.docs;
                    if (snap.length > 0)
                      return Expanded(
                        child: ListView.builder(
                          itemCount: snap.length,
                          itemBuilder: (context, index) {
                            return ListItemJobPro(
                              title: snap[index].data()['title'],
                              subtitle: snap[index].data()['companyName'],
                              onTap: (){
                                final id = snap[index].id;
                                Navigator.push(context,MaterialPageRoute(builder: (context) => AppliedPeopleScreen(jobid: id,jobSnap : snap[index])),);
                              },
                              onLongPress: (){
                                _showAlertDialog(index,snap);
                              },
                            );
                          },
                        ),
                      );
                    else
                      return Expanded(
                        child: Center(
                          child: FlatButton(
                            onPressed: (){
                            },
                            child: Text(
                              'Post A Job',
                              style: TextStyle(
                                  fontSize:
                                      MediaQuery.of(context).size.height / 30),
                            ),
                          ),
                        ),
                      );
                  } else
                    return CircularProgressIndicator();
                }),
          ],
        )
      ],
    ));
  }

  void _showAlertDialog(int index, List<QueryDocumentSnapshot> snap) {
    Alert(
      context: context,
      type: AlertType.error,
      title: "Do you want to delete Job",
      buttons: [
        DialogButton(
          child: Text(
            "Yes",
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
          onPressed: () {
            FirebaseFirestore.instance
                .collection('jobs')
            .doc(snap[index].id)
            .delete()
            .then((value){
              Navigator.pop(context);
            });

          },
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
  }
}

class CurvePainter extends CustomPainter {
  @override
  void paint(Canvas canvas, Size size) {
    var paint = Paint();
    paint.color = Colors.redAccent;
    paint.style = PaintingStyle.fill; // Change this to fill

    var path = Path();

    path.moveTo(0, size.height * 0.25);
    path.quadraticBezierTo(
        size.width / 2, size.height / 2, size.width, size.height * 0.25);
    path.lineTo(size.width, 0);
    path.lineTo(0, 0);

    canvas.drawPath(path, paint);
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) {
    return true;
  }
}

class ListItemJobPro extends StatelessWidget {
  final title;
  final subtitle;
  final leading;
  final Function onTap;
  final Function onLongPress;
  ListItemJobPro({this.title, this.subtitle, this.leading, this.onTap, this.onLongPress});

  @override
  Widget build(BuildContext context) {
    return new ListTile(
      onLongPress: onLongPress,
      title: new Text(title ?? 'UI Developer(Strong in Angular) ',
          style: new TextStyle(fontWeight: FontWeight.w500, fontSize: 20.0)),
      subtitle: new Text(subtitle ?? 'Accenture',
          style: new TextStyle(fontWeight: FontWeight.w400, fontSize: 20.0)),
      leading: leading ??
          Icon(
            Icons.work,
            color: Colors.blue[500],
          ),
      onTap: onTap,
    );
  }
}
