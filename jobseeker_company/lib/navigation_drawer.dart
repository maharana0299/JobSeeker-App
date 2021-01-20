import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:jobseeker_company/screens/about_us.dart';
import 'package:jobseeker_company/screens/contact_us.dart';
import 'package:jobseeker_company/screens/home_screen.dart';
import 'package:jobseeker_company/screens/login_screen.dart';
import 'package:jobseeker_company/screens/post_job_screen.dart';
import 'package:jobseeker_company/utils/firebase_helper.dart';

class NavigationDrawer extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        padding: EdgeInsets.zero,
        children: <Widget>[
          createDrawerHeader(),
          createDrawerBodyItem(
              icon: Icons.home,
              text: 'Jobs',
              onTap: (){
                Navigator.pushReplacementNamed(context,HomeScreen.id);
              }
          ),

          createDrawerBodyItem(
              icon: Icons.add,
              text: 'Post Job',
              onTap: () {
                // Not req
                Navigator.pushNamed(context, JobPosted.id);
              }
          ),
          createDrawerBodyItem(
              icon: Icons.logout,
              text: 'Logout',
              onTap: () {
                /// for deleting the saved items
                FirebaseAuth.instance.signOut().then((value){
                  Navigator.pushNamed(context, LoginScreen.id);
                  FirebaseHelper.instance.user = null;
                });
              }
          ),
          Divider(),
          // createDrawerBodyItem(
          //   icon: Icons.notifications_active,
          //   text: 'Notifications',
          // ),
          createDrawerBodyItem(
              icon: Icons.contact_phone,
              text: 'Contact Us',
              onTap: (){
                //todo
                Navigator.pushReplacementNamed(context, ContactUsScreen.id);
              }
          ),
          createDrawerBodyItem(
              icon: Icons.info,
              text: 'About Us',
              onTap: (){
                Navigator.pushReplacementNamed(context, AboutUsScreen.id);
              }
          ),
          ListTile(
            title: Text('App version 1.0.0'),
            onTap: () {},
          ),
        ],
      ),
    );
  }
}

Widget createDrawerHeader() {
  return DrawerHeader(
      margin: EdgeInsets.zero,
      padding: EdgeInsets.zero,
      decoration: BoxDecoration(
        image: DecorationImage(
          fit: BoxFit.fill,
          image: AssetImage('assets/bg_header.jpg'),
        ),
      ),
      child: Stack(children: <Widget>[
        Positioned(
            bottom: 12.0,
            left: 16.0,
            child: Text("Welcome",
                style: TextStyle(
                    color: Colors.grey,
                    fontSize: 20.0,
                    fontWeight: FontWeight.w500))),
      ]));
}

Widget createDrawerBodyItem(
    {IconData icon, String text, GestureTapCallback onTap}) {
  return ListTile(
    title: Row(
      children: <Widget>[
        Icon(icon),
        Padding(
          padding: EdgeInsets.only(left: 8.0),
          child: Text(text),
        )
      ],
    ),
    onTap: onTap,
  );
}
