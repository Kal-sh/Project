//logged in profile info for teacher dash and profile
if (typeof window.sessionStorage !== 'undefined') {

    // Retrieve from local storage      
    const userFname = localStorage.getItem('loggedInUserFname');
    const userLname = localStorage.getItem('loggedInUserLname');
    const username = localStorage.getItem('loggedInUsername');
    const userRole = localStorage.getItem('loggedInUserRole');

    // Check if information is retrieved successfully
    if (username && userRole) {
      // Update username element with retrieved value
      const usernameElement = document.getElementById('loggedInUsername');
      usernameElement.textContent = username;
  
      // Update user role element with retrieved value
      const roleElement = document.getElementById('loggedInUserRole');
      roleElement.textContent = userRole;
      const roleElementDropdown = document.getElementById('loggedInUserRoleDropdown');
      roleElementDropdown.textContent = userRole;
      const fullnameElement = document.getElementById('Fullname');
      fullnameElement.textContent = userFname+" "+ userLname;
      const profilefullnameElement = document.getElementById('profilefullnametop');
      profilefullnameElement.textContent = userFname+" "+ userLname;
      const profilefullnametopElement = document.getElementById('profilefullname');
      profilefullnametopElement.textContent = userFname+" "+ userLname;
      const profileroleElement = document.getElementById('profilerole');
      profileroleElement.textContent = userRole;
      const profileusernameElement = document.getElementById('profileusername');
      profileusernameElement.textContent = username;
      
    } else {
      console.error('Error: User information not found in session storage');
    }
  } else {
    console.error('Error: Session storage is not supported by this browser');
  }



// Function to change password
async function changePassword() {
  const oldPassword = document.getElementById('oldpassword').value;
  const newPassword = document.getElementById('newpassword').value;
  const loggedInUsername = localStorage.getItem('loggedInUsername');

  if (!oldPassword || !newPassword || !loggedInUsername) {
    console.error('Old password, new password, or username is missing');
    return;
  }

  const data = { username: loggedInUsername, oldPassword, newPassword };

  try {
    const response = await fetch('http://localhost:3000/updatePassword', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    });

    if (response.ok) {
      const responseData = await response.json();
      if (responseData.success) {
        console.log('Password changed successfully!');
        // Handle success (e.g., show a success message)
      } else {
        console.error('Error:', responseData.message);
        // Handle errors (e.g., show an error message)
      }
    } else {
      console.error('Failed to update password');
    }
  } catch (error) {
    console.error('Fetch error:', error.message);
    // Handle network errors or other exceptions
  }
}

// Get the "Save Changes" button by its ID
const saveChangesButton = document.getElementById('savechangesbtn');

// Add an event listener to the "Save Changes" button
saveChangesButton.addEventListener('click', function(event) {
  event.preventDefault(); // Prevent the default form submission

  // Call the function to change the password
  changePassword();
});

// Get the logout button by its ID
const logoutButton = document.getElementById('Logoutbtn');

// Add an event listener to the logout button
logoutButton.addEventListener('click', async () => {
    try {
        // Clear the token from storage (e.g., localStorage or sessionStorage)
        localStorage.removeItem('accessToken'); // Assuming the token is stored in localStorage

        // Redirect to the login page or perform any necessary action upon logout
        window.location.href = '/login.html'; // Redirect to the login page
    } catch (error) {
        console.error('Error during logout:', error);
        // Handle error during logout
    }
});


 
