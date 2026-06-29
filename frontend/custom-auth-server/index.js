const express = require('express');
const bodyParser = require('body-parser');
const admin = require('firebase-admin');
const serviceAccountPath = process.env.GOOGLE_APPLICATION_CREDENTIALS;

if (!serviceAccountPath) {
  throw new Error("GOOGLE_APPLICATION_CREDENTIALS environment variable is not set");
}

const serviceAccount = require(serviceAccountPath);

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const app = express();
app.use(bodyParser.json());

app.post('/signup', async (req, res) => {
  const { nationalId, phoneNumber, password } = req.body;

  try {
    const userRecord = await admin.auth().createUser({
      uid: nationalId,
      phoneNumber: phoneNumber,
      password: password
    });

    await admin.firestore().collection('users').doc(userRecord.uid).set({
      nationalId: nationalId,
      phoneNumber: phoneNumber
    });

    res.status(201).send({ message: 'User created successfully' });
  } catch (error) {
    console.error('Error creating user:', error);
    res.status(500).send({ error: 'Error creating user' });
  }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
