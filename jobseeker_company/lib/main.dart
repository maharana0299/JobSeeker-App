
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:jobseeker_company/screens/about_us.dart';
import 'package:jobseeker_company/screens/contact_us.dart';
import 'package:jobseeker_company/screens/home_screen.dart';
import 'package:jobseeker_company/screens/login_screen.dart';
import 'package:jobseeker_company/screens/post_job_screen.dart';
import 'package:jobseeker_company/screens/signup_screen.dart';
import 'package:jobseeker_company/screens/splash_screen.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp().catchError((e){
    print(e.toString());
  });
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final pageRoutes = {
    SplashScreens.id: (context) => SplashScreens(),
    LoginScreen.id: (context) => LoginScreen(),
    HomeScreen.id : (context) => HomeScreen(),
    SignupScreen.id : (context) => SignupScreen(),
    JobPosted.id : (context) => JobPosted(),
    AboutUsScreen.id : (context) =>  AboutUsScreen(),
    ContactUsScreen.id : (context) => ContactUsScreen(),

  };

  @override
  Future<void> initState() {
    // TODO: implement initState
    super.initState();
    // FirebaseAuth.instance.signOut();

  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Home demo',
      theme: ThemeData.light().copyWith(
        appBarTheme: AppBarTheme(
          color: Colors.redAccent,
        ),

      ),
      initialRoute: SplashScreens.id,
      routes: pageRoutes,
    );
  }
}
