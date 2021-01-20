import 'dart:async';

import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:jobseeker_company/utils/firebase_helper.dart';

import '../constants.dart';
import 'home_screen.dart';

class SignupScreen extends StatefulWidget {
  static const id = '/signUp';
  _SignupScreenState createState() => _SignupScreenState();
}

class _SignupScreenState extends State<SignupScreen> {
  final name = TextEditingController();
  final email = TextEditingController();
  final pass = TextEditingController();
  final phoneNo = TextEditingController();
  bool _isloading = false;
  Timer t;

  bool showSpinner = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: AnnotatedRegion<SystemUiOverlayStyle>(
        value: SystemUiOverlayStyle.light,
        child: GestureDetector(
          onTap: () => FocusScope.of(context).unfocus(),
          child: Stack(
            children: <Widget>[
              Container(
                height: double.infinity,
                width: double.infinity,
                decoration: BoxDecoration(
                  gradient: LinearGradient(
                    begin: Alignment.topCenter,
                    end: Alignment.bottomCenter,
                    colors: [
                      Color(0xFF73AEF5),
                      Color(0xFF61A4F1),
                      Color(0xFF478DE0),
                      Color(0xFF398AE5),
                    ],
                    stops: [0.1, 0.4, 0.7, 0.9],
                  ),
                ),
              ),
              Container(
                height: double.infinity,
                child: SingleChildScrollView(
                  physics: AlwaysScrollableScrollPhysics(),
                  padding: EdgeInsets.symmetric(
                    horizontal: 40.0,
                    vertical: 80.0,
                  ),
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      Text(
                        'Sign Up',
                        style: TextStyle(
                          color: Colors.white,
                          fontFamily: 'OpenSans',
                          fontSize: 30.0,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      SizedBox(height: 20.0),
                      _buildUsernameTF(),
                      SizedBox(
                        height: 15.0,
                      ),
                      _buildEmailTF(),
                      SizedBox(
                        height: 15.0,
                      ),
                      _buildPhoneTF(),
                      SizedBox(
                        height: 15.0,
                      ),
                      _buildPasswordTF(),
                      _buildLoginBtn(),
                      _buildSignInWithText(),
                    ],
                  ),
                ),
              ),
              if (_isloading == true)
                Opacity(
                  opacity: 1,
                  child: Center(
                    child: CircularProgressIndicator(),
                  ),
                ),
            ],
          ),
        ),
      ),
    );
  }

  Future<void> signUp() async {
    final _auth = FirebaseAuth.instance;

    _isloading = true;
    setState(() {});
    try {
      await _auth
          .createUserWithEmailAndPassword(
              email: email.text.trim(), password: pass.text.trim())
          .then((value) {
        _isloading = false;
        setState(() {});
        User newUser = FirebaseAuth.instance.currentUser;
        FirebaseHelper.instance.user = newUser;
        if (newUser != null) {
          if (newUser.emailVerified == false) {
            // newUser.sendEmailVerification();
          }
          FirebaseStorage.instance
              .ref()
              .child('default_profile.png')
              .getDownloadURL()
              .then((value) {
            FirebaseFirestore.instance
                .collection('company')
                .doc(newUser.uid)
                .set({
              'companyName': name.text.trim(),
              'email': email.text.trim(),
              'id': newUser.uid,
              'createdAt': DateTime.now().millisecondsSinceEpoch.toString(),
              // 'companyName': null,
              // 'skills': null,
              'phone': phoneNo.text.trim(),
              'aboutCompany': null,
              'photoUrl': value,
            });

            newUser.updateProfile(
              displayName: name.text.trim(),
              photoURL: value,
            );
            setState(() {
              _isloading = true;
            });
          });

          Fluttertoast.showToast(
            msg: "Verify Your Email id ",
            toastLength: Toast.LENGTH_SHORT,
            gravity: ToastGravity.CENTER,
            timeInSecForIosWeb: 1,
            backgroundColor: Colors.red,
            textColor: Colors.white,
            fontSize: 16.0,
          );

          Navigator.pushNamed(context, HomeScreen.id,arguments: newUser);
          //
          // t= Timer.periodic(Duration(seconds: 5), (timer) {
          //   checkVerified().then((value){
          //     setState(() {
          //       _isloading = false;
          //       Navigator.pushNamed(context, HomeScreen.id,arguments: newUser.user);
          //     });
          //   });
          // });

        }
        setState(() {
          showSpinner = false;
        });
      });
    } catch (e) {
      setState(() {
        _isloading = false;
      });
      print(e);
      FirebaseHelper.instance.makeToast(e.toString());
    }
  }

  Future<void> checkVerified() async {
    final user = FirebaseAuth.instance.currentUser;
    await user.reload();
    if (user.emailVerified) {
      _isloading = false;
      setState(() {
        t.cancel();
      });
      Navigator.pushNamed(context, HomeScreen.id);
    }
  }

  Widget _buildUsernameTF() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Text(
          'Name',
          style: kLabelStyle,
        ),
        SizedBox(height: 7.0),
        Container(
          alignment: Alignment.centerLeft,
          decoration: kBoxDecorationStyle,
          height: 50.0,
          child: TextField(
            controller: name,
            keyboardType: TextInputType.text,
            style: TextStyle(
              color: Colors.white,
              fontFamily: 'OpenSans',
            ),
            decoration: InputDecoration(
              border: InputBorder.none,
              contentPadding: EdgeInsets.only(top: 13.0),
              prefixIcon: Icon(
                Icons.account_circle,
                color: Colors.white,
              ),
              hintText: 'Enter Company Name',
              hintStyle: kHintTextStyle,
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildEmailTF() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Text(
          'Email',
          style: kLabelStyle,
        ),
        SizedBox(height: 7.0),
        Container(
          alignment: Alignment.centerLeft,
          decoration: kBoxDecorationStyle,
          height: 50.0,
          child: TextField(
            controller: email,
            keyboardType: TextInputType.emailAddress,
            style: TextStyle(
              color: Colors.white,
              fontFamily: 'OpenSans',
            ),
            decoration: InputDecoration(
              border: InputBorder.none,
              contentPadding: EdgeInsets.only(top: 13.0),
              prefixIcon: Icon(
                Icons.email,
                color: Colors.white,
              ),
              hintText: 'Enter your Email',
              hintStyle: kHintTextStyle,
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildPhoneTF() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Text(
          'Phone Number',
          style: kLabelStyle,
        ),
        SizedBox(height: 7.0),
        Container(
          alignment: Alignment.centerLeft,
          decoration: kBoxDecorationStyle,
          height: 50.0,
          child: TextField(
            controller: phoneNo,
            keyboardType: TextInputType.number,
            style: TextStyle(
              color: Colors.white,
              fontFamily: 'OpenSans',
            ),
            decoration: InputDecoration(
              border: InputBorder.none,
              contentPadding: EdgeInsets.only(top: 13.0),
              prefixIcon: Icon(
                Icons.phone,
                color: Colors.white,
              ),
              hintText: 'Enter your phone number',
              hintStyle: kHintTextStyle,
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildPasswordTF() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: <Widget>[
        Text(
          'Password',
          style: kLabelStyle,
        ),
        SizedBox(height: 7.0),
        Container(
          alignment: Alignment.centerLeft,
          decoration: kBoxDecorationStyle,
          height: 50.0,
          child: TextField(
            controller: pass,
            obscureText: true,
            style: TextStyle(
              color: Colors.white,
              fontFamily: 'OpenSans',
            ),
            decoration: InputDecoration(
              border: InputBorder.none,
              contentPadding: EdgeInsets.only(top: 13.0),
              prefixIcon: Icon(
                Icons.lock,
                color: Colors.white,
              ),
              hintText: 'Enter your Password',
              hintStyle: kHintTextStyle,
            ),
          ),
        ),
      ],
    );
  }

  Widget _buildLoginBtn() {
    return Container(
      padding: EdgeInsets.symmetric(vertical: 20.0),
      width: double.infinity,
      child: RaisedButton(
        elevation: 5.0,
        onPressed: () {
          //todo
          signUp();
          // Navigator.pushReplacementNamed(context, HomeScreen.id);
        },
        padding: EdgeInsets.all(15.0),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(30.0),
        ),
        color: Colors.white,
        child: Text(
          'SIGN UP',
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

  Widget _buildSignInWithText() {
    return Column(
      children: <Widget>[
        Text(
          '- OR -',
          style: TextStyle(
            color: Colors.white,
            fontWeight: FontWeight.w400,
          ),
        ),
        SizedBox(height: 15.0),
        Text(
          'Sign In',
          style: kLabelStyle,
        ),
      ],
    );
  }

  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();
    t.cancel();
  }
}
