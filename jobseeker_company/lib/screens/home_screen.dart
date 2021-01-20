import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../navigation_drawer.dart';
import 'chat_screen.dart';
import 'jobs_posted_screen.dart';
import 'profile_screen.dart';

class HomeScreen extends StatefulWidget {
  static const id = '/homeScreen';
  HomeScreen({Key key}) : super(key: key);
  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {

  int _selectedPage = 0;
  final _pageOptions = [
    JobsPosted(),
    ChatPage(),
    ProfilePage(),
  ];


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar:  AppBar(
        title: Text('Welcome ${FirebaseAuth.instance.currentUser.displayName}'),
        // actions: [
        //   FlatButton(onPressed: (){
        //     FirebaseAuth.instance.signOut().then((value){
        //       Navigator.pushNamed(context, LoginScreen.id);
        //     });
        //   }, child: Text(
        //     'Signout? '
        //   ))
        // ],
      ),
      drawer: NavigationDrawer(),
      body: _pageOptions[_selectedPage],
      bottomNavigationBar: buildBottomNavigationBar(),
    );
  }

  Widget buildBottomNavigationBar() {
    return BottomNavigationBar(
      currentIndex: _selectedPage,
      onTap: (int index) {
        setState(() {
          _selectedPage = index;
        });
      },
      items: [
        BottomNavigationBarItem(
          backgroundColor: Color(0xFf018786),
          title:
          Text('Jobs', style: TextStyle(fontSize: 20, color: Colors.white)),
          icon: Icon(Icons.home, size: 30.0, color: Colors.redAccent),
        ),
        BottomNavigationBarItem(
          title: Text('Chat',
              style: TextStyle(fontSize: 20, color: Colors.white)),
          icon: Icon(Icons.message, size: 30.0, color: Colors.redAccent),
        ),
        BottomNavigationBarItem(
          title: Text('Profile',
              style: TextStyle(fontSize: 20, color: Colors.white)),
          icon: Icon(Icons.person, size: 30.0, color: Colors.redAccent),
        ),
      ],
    );
  }

}
