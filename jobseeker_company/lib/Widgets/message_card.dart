/*
Message card for the current user
 */
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

/*
Card for message received
 */


class MessageCardSent extends StatelessWidget {
  final message;
  final time;

  //todo add message details
  MessageCardSent({this.message, this.time});

  //temp date
  final String date = '${DateTime.now().hour}:${DateTime.now().minute} ';

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.end,
        children: [
          Material(
            borderRadius: BorderRadius.only(
              topLeft: Radius.circular(30.0),
              bottomLeft: Radius.circular(30.0),
              bottomRight: Radius.circular(40.0),
              topRight: Radius.circular(0.0),
            ),
            elevation: 10.0,
            color: Colors.lightBlueAccent,
            child: ConstrainedBox(
              constraints: BoxConstraints(maxWidth: 300),
              child: Container(
                padding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 20.0),
                child: Text(
                  message ??
                      'Hello, i would like to be a part of your company fsfs seer fsfs xdfs fsfs e sfs',
                  style: TextStyle(
                    color: Colors.white,
                  ),
                ),
              ),
            ),
          ),
          Text(
            time ?? '$date',
            style: TextStyle(fontSize: 12.0, color: Colors.grey),
          ),
        ],
      ),
    );
  }
}
class MessageCardReceived extends StatelessWidget {
  final message;
  final profileUrl;
  final time;
  //todo add message details
  MessageCardReceived({this.message, this.profileUrl, this.time});

  //temp date
  final String date = '${DateTime.now().hour}:${DateTime.now().minute} ';

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Container(
            width: 25.0,
            height: 25.0,
            decoration: new BoxDecoration(
              shape: BoxShape.circle,
              image: new DecorationImage(
                fit: BoxFit.fill,
                image: new NetworkImage(profileUrl ??
                    "https://i.imgur.com/BoN9kdC.png"), //profile image
              ),
            ),
          ),
          Material(
            borderRadius: BorderRadius.only(
              topLeft: Radius.circular(0.0),
              bottomLeft: Radius.circular(30.0),
              bottomRight: Radius.circular(30.0),
              topRight: Radius.circular(30.0),
            ),
            elevation: 10.0,
            color: Colors.white,
            child: ConstrainedBox(
              constraints: BoxConstraints(maxWidth: 300),
              child: Container(
                padding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 20.0),
                child: Text(
                  message ??
                      'be ready for it',
                  style: TextStyle(
                    color: Colors.black,
                  ),
                ),
              ),
            ),
          ),
          Container(
            padding: EdgeInsets.only(left: 15.0),
            child: Text(
              time ?? '$date',
              style: TextStyle(fontSize: 12.0, color: Colors.grey),
            ),
          ),
        ],
      ),
    );
  }
}

class MessageCard extends StatelessWidget {
  final message;
  final DateTime time;
  final sentBy;
  final url;

  //todo add message details
  MessageCard({this.message, this.time,this.sentBy,this.url});

  // var date ='${DateTime.now().hour}:${DateTime.now().minute} ';
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Column(
        crossAxisAlignment: sentBy == FirebaseAuth.instance.currentUser.uid ? CrossAxisAlignment.end : CrossAxisAlignment.start,
        children: [
          // if(sentBy != FirebaseAuth.instance.currentUser.uid)
          //   Container(
          //     width: 25.0,
          //     height: 25.0,
          //     decoration: new BoxDecoration(
          //       shape: BoxShape.circle,
          //       image: new DecorationImage(
          //         fit: BoxFit.fill,
          //         image: new NetworkImage(url), //profile image
          //       ),
          //     ),
          //   ),
          Material(
            borderRadius: BorderRadius.only(
              topLeft: sentBy == FirebaseAuth.instance.currentUser.uid ? Radius.circular(30.0) : Radius.circular(0.0),
              bottomLeft: Radius.circular(30.0),
              bottomRight: Radius.circular(40.0),
              topRight:sentBy == FirebaseAuth.instance.currentUser.uid ? Radius.circular(0.0) : Radius.circular(30.0),
            ),
            elevation: 10.0,
            color: Colors.lightBlueAccent,
            child: ConstrainedBox(
              constraints: BoxConstraints(maxWidth: 300),
              child: Container(
                padding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 20.0),
                child: Text(
                  message ??
                      'Hello, i would like to be a part of your company fsfs seer fsfs xdfs fsfs e sfs',
                  style: TextStyle(
                    color: Colors.white,
                  ),
                ),
              ),
            ),
          ),
          Text(
            '${time.hour}-${time.minute}',
            style: TextStyle(fontSize: 12.0, color: Colors.grey),
          ),
        ],
      ),
    );
  }
}