const express = require('express');
require( 'datatables.net-dt' );
const mongoose = require('mongoose');
const cors = require('cors');
const bcrypt = require('bcrypt');
const session = require('express-session');
const jwt = require('jsonwebtoken');
const path = require('path');




const app = express();

app.use(cors());
const port = 3000;


app.use(session({
    secret: '1234',
    resave: false,
    saveUninitialized: false
}));

const mongoUrl = 'mongodb://localhost:27017/Exambank';

const dataSchema = new mongoose.Schema({
    firstName: String,
    lastName: String,
    username: String,
    hilcoeID: String,
    password: String,
    role: { type: String, default: 'student' },
});




const DataModel = mongoose.model('Accounts', dataSchema);

app.use(express.static('public'));
app.use(express.json());

mongoose.connect(mongoUrl);

app.post('/insertdata', async (req, res) => {
    console.log('Received POST request at /insertData');
    const data = req.body;

    if (!data.username || !data.password || !data.hilcoeID) {
        return res.status(400).json({ success: false, message: 'Username, password and ID are required' });
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
    const { username, password } = req.body;

    try {
        const user = await DataModel.findOne({ username });

        if (user && await bcrypt.compare(password, user.password)) {
            console.log('Login successful:', user);

            // Create a JWT token
            const token = jwt.sign(
                { username: user.username, role: user.role },
                '1234', // Replace 'your_secret_key' with a strong secret key
                { expiresIn: '1h' } // Token expiry time
            );

            res.json({
                success: true,
                message: 'Login successful',
                token: token, // Send the token to the frontend
                role: user.role,
                firstName: user.firstName,
                lastName: user.lastName,
                username: user.username
            });
        } else {
            console.error('Invalid credentials');
            res.status(401).json({ success: false, message: 'Invalid credentials' });
        }
    } catch (error) {
        console.error('Error during login:', error);
        res.status(500).json({ success: false, message: 'Internal server error' });
    }
});


/*

*/

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
  
      const isOldPasswordCorrect = await bcrypt.compare(oldPassword, user.password);
  
      if (!isOldPasswordCorrect) {
        return res.status(401).json({ success: false, message: 'Incorrect old password' });
      }
  
      const hashedNewPassword = await bcrypt.hash(newPassword, 10);
      user.password = hashedNewPassword;
  
      await user.save();
  
      res.json({ success: true, message: 'Password updated successfully' });
    } catch (err) {
      console.error('Error updating password:', err);
      res.status(500).json({ success: false, message: 'Internal server error' });
    }
  });
  

app.post('/logout', (req, res) => {
    // Destroy the session to logout the user
    req.session.destroy((err) => {
        if (err) {
            console.error('Error destroying session:', err);
            res.status(500).json({ success: false, message: 'Error logging out' });
        } else {
            // Session destroyed successfully
            res.json({ success: true, message: 'Logged out successfully' });
        }
    });
});

//add course


const courseSchema = new mongoose.Schema({
    courseCode: String,
    courseName: String,
});

const CourseModel = mongoose.model('courses', courseSchema);

// Endpoint to add a new course
app.post('/addCourse', async (req, res) => {
    const { courseCode, courseName } = req.body;

    if (!courseCode || !courseName) {
        return res.status(400).json({ success: false, message: 'Course code and name are required' });
    }

    try {
        const existingCourse = await CourseModel.findOne({ courseCode });
        if (existingCourse) {
            return res.status(400).json({ success: false, message: 'Course code already exists' });
        }

        const newCourse = new CourseModel({
            courseCode,
            courseName,
        });

        const savedCourse = await newCourse.save();
        res.json({ success: true, message: 'Course added successfully', course: savedCourse });
    } catch (err) {
        res.status(500).json({ success: false, message: err.message });
    }
});

app.use('/getCourses', cors());



app.get('/getCourses', async (req, res) => {
    try {
        const courses = await CourseModel.find({}, 'courseCode courseName');
        res.json({ success: true, courses });
    } catch (err) {
        res.status(500).json({ success: false, message: err.message });
    }
});




//exam schema
const examSchema = new mongoose.Schema({
    examName: {
        type: String,
        required: true
    },
    course: {
        type: String, 
        required: true
    },
    allottedTime: {
        type: String,
        required: true
    },
    date: {
        type: Date,
        required: true,
        default: Date.now
    },
    questions: [
        {
            questionText: {
                type: String,
                required: true
            },
            options: [String], // Array to store multiple choice options
            correctAnswer: {
                type: String,
                required: true
            }
        }
    ]
});
//redirect to display Q
app.post('/redirectDisplay', (req, res) => {
    try {
        // Redirect to the display page
        res.redirect('diplay and edit exam.html');

        // Note: You can customize the route as needed.
        // If '/display' is a static HTML page, the redirection should work as expected.
        // Make sure the route matches your file structure.

    } catch (error) {
        console.error('Error redirecting:', error);
        res.status(500).json({ success: false, message: 'Internal server error' });
    }
});


// Model for the exam
const ExamModel = mongoose.model('Exams', examSchema);

module.exports = ExamModel;

// Endpoint to add an exam
app.post('/addExam', async (req, res) => {
    try {
        const examData = req.body;

        console.log('Received Exam Data:', examData); // Log received exam data

        // Create a new exam using the ExamModel and save it to the database
        const newExam = new ExamModel(examData);
        const savedExam = await newExam.save();

        console.log('Saved Exam:', savedExam); // Log saved exam data

        res.status(200).json({ success: true, message: 'Exam added successfully', exam: savedExam });
    } catch (error) {
        console.error('Error adding exam:', error);
        res.status(500).json({ success: false, message: 'Internal server error' });
    }
});


const accountSchema = new mongoose.Schema({
    hilcoeID: String, 
    name: String,
    role: String, 
});

const Account = mongoose.model('accounts', accountSchema);

// Route to fetch student accounts
app.get('/student-list', async (req, res) => {
    try {
        const students = await Account.find({ role: 'student' });
        res.json(students);
    } catch (error) {
        console.error('Error fetching student accounts:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});


const Exam = mongoose.model('exams', examSchema);

app.get('/getExams', async (req, res) => {
    try {
        const exams = await Exam.find({}, 'examName course allottedTime');
        res.json({ success: true, exams });
    } catch (err) {
        console.error('Error fetching exams:', err);
        res.status(500).json({ success: false, message: err.message });
    }
});