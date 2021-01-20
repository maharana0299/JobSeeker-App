import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class ChatCard extends StatelessWidget {
  final companyName;
  final image;
  final lastMessage;
  final Function onLongPress;
  final Function onTap;
  final noOfUnreadMessages;

  ChatCard(
      {this.companyName,
        this.image,
        this.lastMessage,
        this.noOfUnreadMessages,
        this.onTap,
        this.onLongPress});

  @override
  Widget build(BuildContext context) {
    const url = 'https://firebasestorage.googleapis.com/v0/b/jobseeker-b68d4.appspot.com/o/default_profile.png?alt=media&token=0f7faba1-d504-45eb-89a8-49ac603ead31';
    return Card(
      // margin: EdgeInsets.only(top: 10.0),
      elevation: 10.0,
      child: ListTile(
        onTap: onTap,
        onLongPress: onLongPress,
        leading: Container(
          width: 50.0,
          height: 50.0,
          decoration: new BoxDecoration(
            shape: BoxShape.circle,
            image: new DecorationImage(
              fit: BoxFit.fill,
              image: new NetworkImage('${image?? url}'),
            ),
          ),
        ),
        title: Text(companyName ?? 'Company Name'),
        // subtitle: Text(
        //   'You are selected for the interview round...',
        //   style: TextStyle(fontSize: 12.0),
        // ),
        // //todo based on massage time , display day or time
        // trailing: Column(
        //   mainAxisAlignment: MainAxisAlignment.spaceAround,
        //   children: [
        //     Text(
        //       '${DateTime.now().hour}:${DateTime.now().minute}',
        //       style: TextStyle(fontSize: 12.0),
        //     ),
        //     /*
        //     If there is an unseen message this will show
        //      */
        //     CircleAvatar(
        //       maxRadius: 10.0,
        //       minRadius: 5.0,
        //       backgroundColor: Colors.lightBlueAccent,
        //       child: Text('2'),
        //     ),
        //   ],
        // ),
      ),
    );
  }
}