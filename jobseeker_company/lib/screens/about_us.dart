import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../navigation_drawer.dart';


class AboutUsScreen extends StatelessWidget {
  static final id = "/aboutUsScreen";
  final String desc = 'Innerwork provides full-range human resources and IT solutions to'
      ' help businesses improve hiring and digital infrastructure for the effective '
      'functioning of the company. Founded with a purpose to find the right balance of '
      'quality hiring and smooth on-boarding, Innerwork believes in understanding the '
      'business inside-out and be a strategic partner in the business journey. Our team, '
      'comprising of experienced human resource and IT professionals, works single-mindedly '
      'to offer customized HR solutions to enterprises so that perfect skill-to-work match could'
      ' be achieved most cost-effectively.';
  final String otherInfo  = 'In short-span of just 2-years, Innerwork has established itself as a trusted recruiting and selection service provider by offering a perfect job “aspiration to position” match through a smart hiring system. Our dedicated team works meticulously to ensure that good people get an excellent job for all types of profiles. Visualizing the challenges startups face in hiring quality talents, we have devised a smart full-service solution to help them remain free of human resource management worries and dedicate quality time in the core business operation. Innerwork HR and IT solutions are all about “workability,” “applicability,” “affordability,” and, of course, the “flexibility." '
      'Our extensive network of experienced HR professionals is in-sync with the contemporary business realities and offers custom solutions suitable to specific business requirements. We have a robust system in place to ensure quality service to make businesses flourish in competitive space. Our team pays special attention to understanding business and offer tailored solutions to meet the desired objectives. Our start-to-finish approach, along with warm hand-holding, helps us win hearts. This is what we value the most. We leave no stone unturned to strengthen the relationship and help businesses move to the next level. '
      'We believe that the success of a business depends on “action,” and it is the quality of the hiring and workforce management that brings ideas in action. Our work-process has been designed to help you hire suitable candidates so that business ideas could be executed smoothly. Our robust system helps businesses develop the right HR protocols for stable, safe, and productive functioning of the workforce. We are fully equipped with quality professionals and advanced technologies to work with companies of all sizes and scales. We are here to show you the right path, walk with you on the path with a focus on productivity improvement and effective functioning.';
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      drawer: NavigationDrawer(),
      appBar: AppBar(
        backgroundColor: Color(0xFf018786),
      ),
      body: SafeArea(
        child: Stack(children: <Widget>[
          Column(
            children: [
              Container(
                padding: const EdgeInsets.all(0.0),
                alignment: Alignment.center,
                width: double.infinity,
                height: 200,
                decoration: BoxDecoration(
                  color: Color(0xFf018786),
                  borderRadius: BorderRadius.only(
                    bottomRight: Radius.circular(0),
                    bottomLeft: Radius.circular(0),
                  ),
                ),
                child: Center(
                    child: Text(
                      'About Us',
                      style: TextStyle(
                          color: Colors.white,
                          fontWeight: FontWeight.bold,
                          fontSize: 30),
                    )),
              ),
            ],
          ),
          SingleChildScrollView(
            child: Container(
              margin:
              EdgeInsets.only(top: 170, left: 20, right: 20, bottom: 300),
              padding: const EdgeInsets.all(0.0),
              alignment: Alignment.center,
              width: double.infinity,
              decoration: BoxDecoration(
                color: Color(0xff03adc6),
                borderRadius: BorderRadius.only(
                  topRight: Radius.circular(50),
                  topLeft: Radius.circular(50),
                  bottomLeft: Radius.circular(50),
                  bottomRight: Radius.circular(50),
                ),
              ),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: [
                  Image(image: NetworkImage('https://www.innerworkindia.com/Images/about-us.jpg'),),
                  Text(
                    'What We Are',
                    style: TextStyle(
                      fontSize: 25,
                      color: Colors.white,
                    ),
                  ),
                  Container(
                    padding: EdgeInsets.all(20),
                    child: Text(
                      '$desc',
                      style: TextStyle(
                        letterSpacing: 3,
                        fontSize: 15,
                        color: Colors.black,
                      ),
                    ),
                  ),
                  Image(image: NetworkImage('https://www.innerworkindia.com/img/Services.jpg')),
                  Container(
                    padding: EdgeInsets.all(20),
                    child: Text(
                      '$otherInfo',
                      style: TextStyle(
                        letterSpacing: 3,
                        fontSize: 15,
                        color: Colors.black,
                      ),
                    ),
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Row(
                        children : [
                          Icon(Icons.call),
                          SizedBox(width: 10,),
                          Text('9887888469'),
                        ],
                      ),
                      Row(

                        children: [
                          Icon(Icons.email),
                          SizedBox(width: 10,),
                          Text('Info@innerworkindia.com')
                        ],
                      ),
                    ],
                  )
                ],
              ),
            ),
          ),
        ],),
      ),
    );
  }
}
