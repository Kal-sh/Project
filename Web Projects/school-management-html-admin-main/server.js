const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const bcrypt = require('bcrypt');

const app = express();
const port = 3000;

app.use(cors());
const mongoUrl = 'mongodb://localhost:27017/users';

const dataSchema = new mongoose.Schema({
    firstName: String,
    lastName: String,
    username: String,
    password: String,
    role: { type: String, default: 'student' },
});




const DataModel = mongoose.model('Data', dataSchema);

app.use(express.static('public'));
app.use(express.json());

mongoose.connect(mongoUrl);

app.post('/insertdata', async (req, res) => {
    console.log('Received POST request at /insertData');
    const data = req.body;

    if (!data.username || !data.password) {
        return res.status(400).json({ success: false, message: 'Username and password are required' });
    }

    try {
        const existingUser = await DataModel.findOne({ username: data.username });
        if (existingUser) {
            return res.status(400).json({ success: false, message: 'Username already exists' });
        }

        const hashedPassword = await bcrypt.hash(data.password, 10);
        data.password = hashedPassword;

        const result = await DataModel.create(data);

        console.log('Data inserted:', result);
        res.json({ success: true, message: 'Data inserted successfully' });
    } catch (error) {
        console.error('Error inserting data:', error);
        res.status(500).json({ success: false, message: 'Internal server error' });
    }
});

app.post('/login', async (req, res) => {
    console.log('Received POST request at /login');
    const { username, password } = req.body;

    try {
        const user = await DataModel.findOne({ username });

        if (user && await bcrypt.compare(password, user.password)) {
            console.log('Login successful:', user);
            res.json({ success: true, message: 'Login successful', role: user.role });
        } else {
            console.error('Invalid credentials');
            res.status(401).json({ success: false, message: 'Invalid credentials' });
        }
    } catch (error) {
        console.error('Error during login:', error);
        res.status(500).json({ success: false, message: 'Internal server error' });
    }
});

app.post('/addteacher', async (req, res) => {
    const { username, password } = req.body;

    if (!username || !password) {
        return res.status(400).json({ success: false, message: 'Username and password are required' });
    }

    try {
        const existingUser = await DataModel.findOne({ username });
        if (existingUser) {
            return res.status(400).json({ success: false, message: 'Username already exists' });
        }

        const hashedPassword = await bcrypt.hash(password, 10);

        const newTeacher = new DataModel({
            username,
            password: hashedPassword,
            role: 'teacher'
        });

        const savedTeacher = await newTeacher.save();
        res.json({ success: true, message: 'Teacher account created', teacher: savedTeacher });
    } catch (err) {
        res.status(500).json({ success: false, message: err.message });
    }
});

app.get('/getteachers', async (req, res) => {
    try {
        const teachers = await DataModel.find({ role: 'teacher' });
        res.json({ success: true, teachers });
    } catch (err) {
        res.status(500).json({ success: false, message: err.message });
    }
});

app.listen(port, () => {
    console.log(`Server is running at http://localhost:${port}`);
});

// Add a new endpoint to retrieve user information


app.use(express.json());

// Add a new endpoint to retrieve user information
app.get('/getUserInfo', async (req, res) => {
    try {
        const { username } = req.query;

        if (!username) {
            return res.status(400).json({ success: false, message: 'Username is required' });
        }

        const user = await DataModel.findOne({ username });

        if (!user) {
            return res.status(401).json({ success: false, message: 'User not found' });
        }

        // Respond with user information
        res.json({
            success: true,
            firstName: user.firstName,
            lastName: user.lastName,
            username: user.username,
            role: user.role,
        });
    } catch (error) {
        console.error('Error retrieving user information:', error);
        res.status(500).json({ success: false, message: 'Internal server error' });
    }
});


app.post('/updatePassword', async (req, res) => {
    const { username, oldPassword, newPassword } = req.body;
   
    if (!username || !oldPassword || !newPassword) {
      return res.status(400).json({ success: false, message: 'Username, old password, and new password are required' });
    }
   
    try {
      const user = await DataModel.findOne({ username });
      if (!user) {
        return res.status(401).json({ success: false, message: 'User not found' });
      }
   
      // Verify the old password
      if (!await bcrypt.compare(oldPassword, user.password)) {
        return res.status(401).json({ success: false, message: 'Incorrect old password' });
      }
   
      // Hash the new password
      const hashedPassword = await bcrypt.hash(newPassword, 10);
   
      // Update the password
      user.password = hashedPassword;
   
      await user.save();
   
      res.json({ success: true, message: 'Password updated successfully' });
    } catch (err) {
      res.status(500).json({ success: false, message: err.message });
    }
   });
   