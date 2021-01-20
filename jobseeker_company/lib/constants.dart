import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

const String kPhoneNo = 'phone';
const String kName = 'name';
const String kAboutMe = 'aboutMe';
const String kSkills = 'skills';
const String kChattingWith = 'chattingWith';
const String kPhotoUrl = 'photoUrl';
const String kResume = 'resume';
const String kQuery = 'queriesFromUsers';

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

final customTheme = ThemeData(
  primarySwatch: Colors.blue,
  brightness: Brightness.dark,
  accentColor: Colors.redAccent,
  inputDecorationTheme: InputDecorationTheme(
    border: OutlineInputBorder(
      borderRadius: BorderRadius.all(Radius.circular(4.00)),
    ),
    contentPadding: EdgeInsets.symmetric(
      vertical: 12.50,
      horizontal: 10.00,
    ),
  ),
);

// final kTextFieldDecoration = InputDecoration(
//     border: InputBorder.none,
//     hintText: 'Enter a search term',
//     prefixIcon: Icon(FontAwesomeIcons.search),
//   enabledBorder: OutlineInputBorder(
//     borderSide: BorderSide(
//       color: Colors.blueAccent,
//     ),
//     borderRadius: BorderRadius.circular(10.0),
//   ),
// );

InputDecoration kTextFieldEditDecoration(VoidCallback onPressed,{hint}) {
  return InputDecoration(
    filled: true,
    fillColor: Colors.black,

    suffixIcon: IconButton(
        onPressed: onPressed,
        icon: Icon(
          Icons.done,
          color: Colors.white,
        )),
    hintText: hint??'Enter Changes',
    hintStyle: TextStyle(
      color: Colors.white,
    ),
    focusColor: Colors.greenAccent,
    border: OutlineInputBorder(
      borderRadius: BorderRadius.all(
        Radius.circular(10.0),
      ),
      borderSide: BorderSide.none,
    ),
  );
}

const kTextFieldDecoration = InputDecoration(
  filled: true,
  fillColor: Colors.redAccent,
  suffixIcon: IconButton(
      icon: Icon(
    Icons.done,
    color: Colors.white,
  )),
  hintText: 'Enter Changes',
  hintStyle: TextStyle(
    color: Colors.white,
  ),
  focusColor: Colors.greenAccent,
  border: OutlineInputBorder(
    borderRadius: BorderRadius.all(
      Radius.circular(10.0),
    ),
    borderSide: BorderSide.none,
  ),
);

/*
For SigninScreen
 */

final kHintTextStyle = TextStyle(
  color: Colors.white54,
  fontFamily: 'OpenSans',
);

final kLabelStyle = TextStyle(
  color: Colors.white,
  fontWeight: FontWeight.bold,
  fontFamily: 'OpenSans',
);

final kBoxDecorationStyle = BoxDecoration(
  color: Color(0xFF6CA8F1),
  borderRadius: BorderRadius.circular(10.0),
  boxShadow: [
    BoxShadow(
      color: Colors.black12,
      blurRadius: 6.0,
      offset: Offset(0, 2),
    ),
  ],
);

List<String> kWorkExperience = ['0-1','2-3','4-5','5+'];
List<String> kQualification = ['12th','B.Tech','BA','BCom','BSc','Msc','M.Tech'];
List<String> kJobType = ['job','internship'];
List<String> kStatus = ['Applied','InProcess'];