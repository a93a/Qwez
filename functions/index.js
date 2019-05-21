//init required
const functions = require('firebase-functions');
const admin = require('firebase-admin');
const gcm = require('@google-cloud/storage');

//init app
admin.initializeApp();

//init services
const db = admin.firestore()
const storage = admin.storage()

//init service paths
const highscore_collection = db.collection("highscore")
const user_collection = db.collection("user")
const bucket = storage.bucket('gs://qwez-take-2.appspot.com')

//--------------------- functions -----------------------------

//On user creation, trigger: Add information to /highscore/uid
exports.onUserCreation = functions.auth.user().onCreate((user) => {

	const userid = user.uid
	var usernick = user.displayName

	if(usernick === null){
		usernick = "unknown"
	}

    return highscore_collection.doc(userid).set({
        score: 0,
        nick: usernick,
    }).then(user => {

	 return user_collection.doc(userid).set({
			nick: usernick,
			photo: null
		})
	})

})

//On user deletion, trigger: Delete information from /highscore/uid and image from storage
exports.onUserDeletion = functions.auth.user().onDelete((user) => {

	const userid = user.uid

	return highscore_collection.doc(userid).delete().then(user => {
		return user_collection.doc(userid).delete()
	})

})

//trigger to delete photo on user deleted
exports.deleteUser = functions.firestore.document('user/{userID}').onDelete((snap, context) => {

	const photoName = snap.data().photo;
	const filePath = `user_photo/${photoName}`
	const file = bucket.file(filePath)

	return file.delete()

});

//update highscore nick on user nick update
exports.onUserNickUpdate = functions.firestore.document('user/{userid}').onWrite((change,context) => {

	const changednick = change.after.data().nick
	const userid = context.params.userid;

	return highscore_collection.doc(userid).update({
		nick: changednick
	})

})

//when photo uploaded
exports.onUserPhotoUpload = functions.storage.object().onFinalize(async (object) => {

																																// this below obvisouly will only work in case of foldername == user_photo
	const filePath = object.name;																	//this is something like user_photo/
	const fileNameWithExtension = filePath.substring(11) 					//remove user_photo/ from const filepath
	const user = fileNameWithExtension.replace(/\.[^/.]+$/, "") 	//remove extension from const fileNameWithExtension
	const folder = filePath.substring(0, 11) 											// get user_photo from const filePath

	console.log(`path: ${filePath} and fileNameWithExtension: ${fileNameWithExtension} and user: ${user} and folder ${folder} `)

	if(folder.startsWith('user_photo/')){
		console.log('folder start with user_photo/')
		return user_collection.doc(user).update({
			photo: fileNameWithExtension
		})
	}else{
		return null
	}

});
