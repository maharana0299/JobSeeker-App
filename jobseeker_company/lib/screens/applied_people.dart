import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:jobseeker_company/screens/view_profile_screen.dart';

import 'jobs_posted_screen.dart';

class AppliedPeopleScreen extends StatefulWidget {
  final QueryDocumentSnapshot jobSnap;
  final String jobid ;
  AppliedPeopleScreen({this.jobid, this.jobSnap});
  
  @override
  _AppliedPeopleScreenState createState() => _AppliedPeopleScreenState();
}

class _AppliedPeopleScreenState extends State<AppliedPeopleScreen> {
  
  @override
  Widget build(BuildContext context) {
    final ref = FirebaseFirestore.instance.collection('jobs').doc(widget.jobid).collection('appliedPeople');
    return Scaffold(
      appBar: AppBar(),
      body: SafeArea(
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
                        'Applied People',
                        style: TextStyle(
                            color: Colors.white,
                            fontSize: MediaQuery.of(context).size.height / 30),
                      ),
                    ),
                  ),
                  StreamBuilder<QuerySnapshot>(
                      stream:ref
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
                                    title: snap[index].data()['name'],
                                    subtitle: snap[index].data()['status'],
                                    onTap: (){
                                      final ref = snap[index].data()['appliedBy'];

                                      Navigator.push(context,MaterialPageRoute(builder: (context) => ViewUserProfile(ref: ref,snap : snap[index],jobSnap : widget.jobSnap),),);
                                    },
                                    onLongPress: (){
                                      // _showAlertDialog(index,snap);
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
          )),
    );
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
