import 'dart:async';

import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:jobseeker_company/screens/signup_screen.dart';
import 'package:jobseeker_company/utils/firebase_helper.dart';

import 'home_screen.dart';

class LoginScreen extends StatefulWidget {
  static const id = '/loginScreen';
  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  bool _isLoading = false;
  final key = GlobalKey();
  final useEdit = TextEditingController();
  final passEdit = TextEditingController();
  Timer timer;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    // check();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          Login(
              key: key,
              user: useEdit,
              pass: passEdit,
              onSubmit: () {
                // print("Submit");
                _isLoading = true;
                setState(() {});
                print('${useEdit.value}');
                login(useEdit.text.trim(), passEdit.text.trim());
              }),
          if (_isLoading)
            Opacity(
              opacity: 0.5,
              child: Center(
                child: CircularProgressIndicator(),
              ),
            ),
        ],
      ),
    );
  }

  Future<void> login(String email, String password) async {
    try {
      await FirebaseAuth.instance
          .signInWithEmailAndPassword(email: email, password: password)
          .then((_) {
        User user = FirebaseAuth.instance.currentUser;
        if (user != null) {
          // if (user.emailVerified == false)
          //   Fluttertoast.showToast(
          //     msg: "Email Not Verified",
          //     toastLength: Toast.LENGTH_SHORT,
          //     gravity: ToastGravity.CENTER,
          //     timeInSecForIosWeb: 1,
          //     backgroundColor: Colors.red,
          //     textColor: Colors.white,
          //     fontSize: 16.0,
          //   );
          // FirebaseHelper.instance.user = user;
          // timer = Timer.periodic(Duration(seconds: 5), (timer) {
          //   checkVerified().then((value){
          //     setState(() {
          //       _isLoading = false;
          //     });
          //   });
          // });
          FirebaseFirestore.instance
              .collection('users')
              .where('id', isEqualTo: user.uid)
              .get()
              .then((value) {
            if (value.docs.isNotEmpty) {
              FirebaseHelper.instance
                  .makeToast('You cant sign in with this account!!');
              FirebaseAuth.instance.signOut();
            } else {
              FirebaseHelper.instance
                  .makeToast('Successful!!');
              // FirebaseAuth.instance.signOut();
              Navigator.pushNamed(context, HomeScreen.id, arguments: user);
            }
          });
        } else {
          if (user == null) {
            throw FirebaseAuthException(message: "User Not Found");
          }
          Fluttertoast.showToast(
            msg: "User Doesn't Exist",
            toastLength: Toast.LENGTH_SHORT,
            gravity: ToastGravity.CENTER,
            timeInSecForIosWeb: 1,
            backgroundColor: Colors.red,
            textColor: Colors.white,
            fontSize: 16.0,
          );
        }
        setState(() {
          _isLoading = false;
        });
      });
    } on FirebaseAuthException catch (e) {
      print(e);
      Fluttertoast.showToast(
        msg: e.toString().trim(),
        toastLength: Toast.LENGTH_SHORT,
        gravity: ToastGravity.CENTER,
        timeInSecForIosWeb: 1,
        backgroundColor: Colors.red,
        textColor: Colors.white,
        fontSize: 16.0,
      );
      setState(() {
        _isLoading = false;
      });
    }
  }

  Future<void> checkVerified() async {
    final user = FirebaseAuth.instance.currentUser;
    await user.reload();
    if (user.emailVerified) {
      _isLoading = false;
      setState(() {
        timer.cancel();
      });
      Navigator.pushNamed(context, HomeScreen.id);
    }
  }
}

class Login extends StatelessWidget {
  const Login({
    Key key,
    @required this.onSubmit,
    @required this.user,
    @required this.pass,
  }) : super(key: key);

  final VoidCallback onSubmit;
  final TextEditingController user;
  final TextEditingController pass;

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Column(
        children: <Widget>[
          Expanded(child: Container()),
          Text(
            'Sign In',
            style: TextStyle(
              color: Colors.black,
              fontFamily: 'OpenSans',
              fontSize: 30.0,
              fontWeight: FontWeight.bold,
            ),
          ),
          Container(
            margin: EdgeInsets.only(top: 50.0, bottom: 20.0),
            padding: const EdgeInsets.only(left: 20.0, right: 20.0),
            child: Material(
              elevation: 10.0,
              borderRadius: BorderRadius.circular(10),
              color: Colors.white,
              child: TextField(
                controller: user,
                decoration: new InputDecoration(
                  hintText: 'Email address',
                  fillColor: Colors.white70,
                  enabledBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(12.0)),
                    borderSide: BorderSide(color: Colors.black, width: 2),
                  ),
                  focusedBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(10.0)),
                    borderSide: BorderSide(color: Colors.green.shade900),
                  ),
                ),
              ),
            ),
          ),
          Container(
            margin: EdgeInsets.only(top: 20.0, bottom: 20.0),
            padding: const EdgeInsets.only(left: 20.0, right: 20.0),
            child: Material(
              elevation: 10.0,
              borderRadius: BorderRadius.circular(10),
              color: Colors.white,
              child: new TextField(
                controller: pass,
                decoration: new InputDecoration(
                  hintText: ' Password',
                  enabledBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(12.0)),
                    borderSide: BorderSide(color: Colors.black, width: 2),
                  ),
                  focusedBorder: OutlineInputBorder(
                    borderRadius: BorderRadius.all(Radius.circular(10.0)),
                    borderSide: BorderSide(color: Colors.green.shade900),
                  ),
                ),
                obscureText: true,
              ),
            ),
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              RaisedButton(
                elevation: 10.0,
                color: Colors.redAccent,
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(18.0),
                    side: BorderSide(color: Colors.red)),
                child: new Text(
                  'Sign In',
                  style: TextStyle(color: Colors.white),
                ),
                onPressed: onSubmit,
              ),
              SizedBox(
                width: 20.0,
              ),
              GestureDetector(
                child: Text('Or SignUp ?'),
                onTap: () {
                  Navigator.pushReplacementNamed(context, SignupScreen.id);
                },
              ),
            ],
          ),
          Expanded(child: Container()),
        ],
      ),
    );
  }
}

/// ######################################################################################################################################################################################
