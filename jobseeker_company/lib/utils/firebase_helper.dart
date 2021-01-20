import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

class FirebaseHelper {
  static FirebaseHelper mHelper;
  FirebaseAuth mAuth = FirebaseAuth.instance;
  User user = FirebaseAuth.instance.currentUser;
  FirebaseFirestore mFiresStore = FirebaseFirestore.instance;

  bool isLogin() {
    if (user != null) {
      return true;
    } else {
      return false;
    }
  }
  static FirebaseHelper get instance {
    if (mHelper == null) {
      mHelper = FirebaseHelper();
    }
    return mHelper;
  }


  void sendVerificationCode(){
    user.sendEmailVerification();
  }
  


  void makeToast(String msg) {
    Fluttertoast.showToast(
      msg: msg,
      toastLength: Toast.LENGTH_SHORT,
      gravity: ToastGravity.CENTER,
      timeInSecForIosWeb: 1,
      backgroundColor: Colors.red,
      textColor: Colors.white,
      fontSize: 16.0,
    );
  }


}
