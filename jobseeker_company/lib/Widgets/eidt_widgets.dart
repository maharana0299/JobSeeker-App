import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:jobseeker_company/utils/firebase_helper.dart';


import '../constants.dart';

class EditChanges extends StatelessWidget {
  final controller = TextEditingController();
  final String name;
  final String field;

  EditChanges({@required this.field, @required this.name}) {
    controller.text = name ?? '';
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Edit Changes'),
      ),
      body: SafeArea(
        child: ListTile(
          title: TextField(
            minLines: 1,
            maxLines: 10,
            keyboardType: TextInputType.multiline,
            controller: controller,
            style: TextStyle(color: Colors.white),
            decoration: kTextFieldEditDecoration(() {
              if (field != null && field.length > 0) {
                FirebaseFirestore.instance
                    .collection('company')
                    .doc(FirebaseHelper.instance.user.uid)
                    .update({field: controller.text}).then((value) {
                  FirebaseHelper.instance.makeToast("Update Successfully");
                }).catchError((error) {
                  FirebaseHelper.instance.makeToast('Error in Updating');
                });
              }
            }),
          ),
        ),
      ),
    );
  }
}

class EditIcon extends StatelessWidget {
  final VoidCallback onPressed;
  final String message;
  final String subTitle;
  final bool showIcon;
  EditIcon(
      {@required this.onPressed,
        @required this.message,
        this.showIcon,
        Key key,
        this.subTitle})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListTile(
      contentPadding: EdgeInsets.only(left: 30, right: 30),
      title: Text(message ?? 'Enter the Field'),
      subtitle: subTitle != null ? Text('$subTitle') : null,
      trailing: showIcon == null || showIcon == true
          ? IconButton(
        icon: Icon(Icons.edit),
        onPressed: onPressed,
      )
          : null,
    );
  }
}

class EditSkills extends StatefulWidget {
  final List skills;
  final String field;

  EditSkills({
    @required this.skills,
    @required this.field,
    Key key,
  }) : super(key: key);

  @override
  _EditSkillsState createState() => _EditSkillsState();
}

class _EditSkillsState extends State<EditSkills> {
  final controller = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Edit Changes'),
      ),
      body: SafeArea(
        child: Column(
          children: [
            ListTile(
              title: TextField(
                controller: controller,
                style: TextStyle(color: Colors.white),
                decoration: kTextFieldEditDecoration(() {
                  if (controller.text.length > 0) {
                    var ls = [];
                    if(widget.skills != null ){widget.skills.add(controller.text);
                    ls = widget.skills;
                    } else {
                      ls = ['${controller.text}'];
                    }

                    FirebaseFirestore.instance
                        .collection('users')
                        .doc(FirebaseHelper.instance.user.uid)
                        .update({
                      'skills': ls,
                    }).then((value) {
                      FirebaseHelper.instance.makeToast('Skill Added');
                      setState(() {
                        controller.clear();
                      });
                    }).catchError((e) {
                      FirebaseHelper.instance.makeToast(e);
                    });
                  }
                }, hint: 'Add More Skills'),
              ),
            ),
            Wrap(
              children: widget.skills != null ? widget.skills
                  .map(
                    (e) => SkillTile(
                  skill: e,
                ),
              )
                  .toList() : [],
            ),
          ],
        ),
      ),
    );
  }
}

class SkillTile extends StatelessWidget {
  final skill;
  final VoidCallback onTappedCancel;
  const SkillTile({Key key, this.skill,this.onTappedCancel}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Stack(children: [
      Material(
        elevation: 5,
        borderRadius: BorderRadius.all(
          Radius.circular(20),
        ),
        color: Colors.red,
        child: Container(
          margin: EdgeInsets.all(10.0),
          padding: EdgeInsets.all(8.0),
          child: Text(
            skill.toString(),
            style: TextStyle(fontSize: 10, color: Colors.white),
          ),
        ),
      ),
      Positioned(
        right: -12,
        top: -12,
        child: IconButton(
          alignment: Alignment.center,
          icon: Icon(
            Icons.cancel,
            color: Colors.white,
          ),
          onPressed: onTappedCancel,
        ),
      )
    ]);
  }
}
