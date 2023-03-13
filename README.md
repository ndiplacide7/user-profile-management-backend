User Profile Manager API
This project provides a set of RESTful APIs for managing user profiles. These APIs allow downstream consumers to create, edit, delete, and suspend user profiles, as well as manage user credentials (username and password), authenticate users, and retrieve user profiles.

API Endpoints
The following API endpoints are available:

POST /user.create_user_profile

This API creates a new user profile.



PUT /user/edit_user_profile/{userId}

This API updates/edits an existing user profile.



POST /user_credential/create_user_credential

This API creates new user credentials (username and password) for a specific user.



PUT /user_credential/{userId}/resetpassword

This API updates the password for a specific user.



POST /api/authenticate

This API authenticates a user based on the provided credentials.



GET /user/get_a_list_of_all_users

This API returns a list of all users.



GET /user/{userId}

This API returns the user profile using user Id.



DELETE /user/delete/{username}

This API that Delete/Suspend user profile