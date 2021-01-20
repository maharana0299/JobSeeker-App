import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:jobseeker_company/utils/firebase_helper.dart';


import '../constants.dart';
import '../navigation_drawer.dart';

class ContactUsScreen extends StatefulWidget {
  static const id = '/contactUsScreen';
  @override
  _ContactUsScreenState createState() => _ContactUsScreenState();
}

class _ContactUsScreenState extends State<ContactUsScreen> {

  final _textEditingController = TextEditingController();
  Widget _buildMessageField(){
    return TextField(
      controller: _textEditingController,
      decoration: InputDecoration(
        //labelText: 'Message',
        //hintText: 'Type Something here',
        border: OutlineInputBorder(),
      ),
      maxLines: 10,
    );
  }
  Widget _buildSendBtn() {
    return Container(
      padding: EdgeInsets.symmetric(vertical: 20.0),
      width: double.infinity,
      child: RaisedButton(
        elevation: 5.0,
        onPressed: () {
          print(_textEditingController.text);
          if (_textEditingController.text.length > 0)
            _sendQuery(_textEditingController.text).then((val){
              FirebaseHelper.instance.makeToast('Query Sent, You will be contacted shortly via email');
              _textEditingController.clear();
            });
        },
        padding: EdgeInsets.all(15.0),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(30.0),
        ),
        color: Colors.lightBlue,
        child: Text(
          'Send',
          style: TextStyle(
            color: Color(0xFF527DAA),
            letterSpacing: 1.5,
            fontSize: 18.0,
            fontWeight: FontWeight.bold,
            fontFamily: 'OpenSans',
          ),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Contact Us'),
      ),
      drawer: NavigationDrawer(),
      body: ListView(
          padding: EdgeInsets.all(8),
          children: <Widget>[
            ListTile(
              title: Text('Leave Your Query Here'),
            ),
            SizedBox(height: 20.0),
            _buildMessageField(),
            SizedBox(height: 30.0),
            _buildSendBtn(),

          ]
      ),
    );
  }

  _sendQuery(String message) async{
    final ref = FirebaseFirestore.instance.collection(kQuery)
        .doc(FirebaseAuth.instance.currentUser.uid + DateTime.now().millisecondsSinceEpoch.toString());
    final msg = {
      'sentBy' : FirebaseAuth.instance.currentUser.uid,
      'timing' : DateTime.now(),
      'query' : message
    };

    ref.set(msg).catchError((e){
      FirebaseHelper.instance.makeToast(e.toString());
    }).then((value) {
      FirebaseHelper.instance.makeToast('You will be contacted Soon. Your Query has been sent successfully');
    });
  }
}

