import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:jobseeker_company/Widgets/chart_Card.dart';
import 'package:jobseeker_company/utils/firebase_helper.dart';
import 'package:rflutter_alert/rflutter_alert.dart';

import 'message_screen.dart';

class ChatPage extends StatefulWidget {
  @override
  _ChatPageState createState() => _ChatPageState();
}

class _ChatPageState extends State<ChatPage> {
  @override
  void initState() {
    super.initState();
  }

  final ref = FirebaseFirestore.instance.collection('company')
      .doc(FirebaseAuth.instance.currentUser.uid)
      .collection('chattingWith');

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SafeArea(
        child: StreamBuilder<QuerySnapshot>(
          stream: ref.snapshots(),
          builder: (BuildContext context, AsyncSnapshot<QuerySnapshot> snapshot){
            if (!snapshot.hasData) {
              return Center(
                child: CircularProgressIndicator(
                  backgroundColor: Colors.lightBlueAccent,
                ),
              );
            }
            else {
              final List<QueryDocumentSnapshot> doc = snapshot.data.docs;
               if (doc.isNotEmpty) return Container(
                child: ListView.builder(
                  itemCount: snapshot.data.docs.length,
                  itemBuilder: (BuildContext bc, int index) {
                    return ChatCard(
                      companyName: doc[index].data()['name'],
                      image: doc[index].data()['photoUrl'],
                      onTap: (){
                        Navigator.push(context, MaterialPageRoute(builder: (context)=> MessageScreen(chattingWith: doc[index])));
                      },
                      onLongPress: (){
                        //todo
                        _showAlertDialog(doc[index]);
                      },
                    );
                  },
                ),
              );
               else return Container(child: Center(child: Text('No Chats Yet'),));
            }
          },
        ),
      ),
    );
  }

  void _showAlertDialog(QueryDocumentSnapshot snap) {

    Alert(
      context: context,
      type: AlertType.error,
      title: "Do you want to delete chat",
      buttons: [
        DialogButton(
          child: Text(
            "Yes",
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
          onPressed: () {
            print(snap.id);
            ref.doc(snap.id).delete().then((value){
              Navigator.pop(context);
              FirebaseHelper.instance.makeToast("Deleted");
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

class ListViewBuilder extends StatefulWidget {
  @override
  _ListViewBuilderState createState() => _ListViewBuilderState();
}

class _ListViewBuilderState extends State<ListViewBuilder> {
  //just to create some random company
  final items = List<String>.generate(20, (i) => "Company ${i + 1}");

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: items.length,
      itemBuilder: (BuildContext bc, int index) {
        return ChatCard(
          companyName: items[index],
          onTap: () {
            Navigator.pushNamed(context, MessageScreen.id,
                arguments: FirebaseAuth.instance.currentUser);
            // Navigator.pushNamed(context, DetailScreen.id);
          },
          onLongPress: () {
            _showAlertDialog(index);
          },
        );
      },
    );
  }

  void _showAlertDialog(int index) {
    Alert(
      context: context,
      type: AlertType.error,
      title: "Do you want to delete contact",
      buttons: [
        DialogButton(
          child: Text(
            "Yes",
            style: TextStyle(color: Colors.white, fontSize: 20),
          ),
          onPressed: () {
            items.removeAt(index);
            setState(() {});
            Navigator.pop(context);
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
