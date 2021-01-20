
import 'package:cloud_firestore/cloud_firestore.dart';

import 'package:firebase_auth/firebase_auth.dart' ;
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:jobseeker_company/Widgets/message_card.dart';


class MessageScreen extends StatefulWidget {
  static const id = 'MessageScreen';
  final QueryDocumentSnapshot chattingWith;

  MessageScreen({this.chattingWith});
  @override
  _MessageScreenState createState() => _MessageScreenState();
}

class _MessageScreenState extends State<MessageScreen> {
  final messageController = TextEditingController();
  String message;


  //demo list of data

  @override
  Widget build(BuildContext context) {
    String chatId = _generateChatId(FirebaseAuth.instance.currentUser.uid );

    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        leading: IconButton(
          icon: Icon(
            Icons.arrow_back_ios_outlined,
            color: Colors.blueAccent,
          ),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
        centerTitle: true,
        title: Text(
          widget.chattingWith.data()['name'],
          style: TextStyle(color: Colors.black),
        ),
        actions: [
          IconButton(
            icon: Icon(
              Icons.info,
              color: Colors.red,
            ),
            onPressed: () {
              //todo open profile of company or person
            },
          )
        ],
      ),
      body: SafeArea(
          child:
          Column(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              StreamBuilder<QuerySnapshot>(
                  stream: FirebaseFirestore.instance.collection('chats').doc(chatId).collection('messages').orderBy('time').snapshots(),
                  builder: (context, snapshot) {
                    if (!snapshot.hasData)
                      return Center(
                        child: CircularProgressIndicator(
                          backgroundColor: Colors.lightBlueAccent,
                        ),
                      );
                    else {
                      final messages = snapshot.data.docs.reversed.toList();


                      return Expanded(
                          child: ListView.builder(
                              reverse: true,
                              itemCount: messages.length,
                              itemBuilder: (context, index) {
                                return MessageCard(
                                  message: messages[index].data()['message'],
                                  time: DateTime.fromMillisecondsSinceEpoch(messages[index].data()['time']),
                                  sentBy: messages[index].data()['sentBy'],
                                  url : widget.chattingWith.data()['photoUrl'],
                                );
                              }));
                    }
                  }
              ),
              Container(
                decoration: kMessageContainerDecoration,
                child: Material(
                  color: Colors.white,
                  elevation: 10.0,
                  borderRadius: BorderRadius.only(
                      topLeft: Radius.circular(30.0),
                      bottomLeft: Radius.circular(30.0),
                      bottomRight: Radius.circular(30.0),
                      topRight: Radius.circular(30.0)),
                  child: Row(
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: <Widget>[
                      Expanded(
                        child: TextField(
                          controller: messageController,
                          onChanged: (value) {
                            //Do something with the user input.
                            message = value;
                          },
                          decoration: kMessageTextFieldDecoration,
                        ),
                      ),
                      IconButton(
                          color: Colors.red,
                          onPressed: () {
                            if (message.replaceAll(' ', '') != '') {
                              FirebaseFirestore.instance.collection('chats').doc(chatId).collection('messages').add({
                                'message': message,
                                'time': DateTime.now().millisecondsSinceEpoch,
                                'sentBy':FirebaseAuth.instance.currentUser.uid,
                              });
                            }
                            setState(() {});
                            messageController.clear();
                            message = "";
                          },
                          icon: Icon(
                            Icons.send_sharp,
                          )),
                    ],
                  ),
                ),
              ),
            ],
          )
      ),
    );
  }

  String _generateChatId(String uid) {
    String id = widget.chattingWith.id;
    if (id.compareTo(uid) <= 0)
      return id + '-' + uid;
    else return uid + '-' + id;
  }
}


const kMessageTextFieldDecoration = InputDecoration(
  contentPadding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 20.0),
  hintText: 'Type your message here...',
  border: InputBorder.none,
);

const kMessageContainerDecoration = BoxDecoration(
  border: Border(
    top: BorderSide(color: Colors.lightBlueAccent, width: 2.0),
  ),
);

const kITextFeildDecoration = InputDecoration(
  hintText: 'Enter your E-mail',
  hintStyle: TextStyle(color: Colors.blue),
  contentPadding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 20.0),
  border: OutlineInputBorder(
    borderRadius: BorderRadius.all(Radius.circular(32.0)),
  ),
  enabledBorder: OutlineInputBorder(
    borderSide: BorderSide(color: Colors.blueAccent, width: 1.0),
    borderRadius: BorderRadius.all(Radius.circular(32.0)),
  ),
  focusedBorder: OutlineInputBorder(
    borderSide: BorderSide(color: Colors.blueAccent, width: 2.0),
    borderRadius: BorderRadius.all(Radius.circular(32.0)),
  ),
);
