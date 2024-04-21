package com.vang.shareimageazure.constant;

public class Common {

    public static final String AUTHORIZATION = "Authorization";

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    public static final String BEARER = "Bearer ";

    public static final String ROLE_USER = "ROLE_USER";

    public static final String AZURE_CONNECTION_STRING = "DefaultEndpointsProtocol=https;AccountName=minimicroservice;AccountKey=B2RqjNpCJfbtID2vCJEj6nYqAhBqp6UVSh5EKL9JWFle+1ciobKvffnh+Ia1KBwI0Ua6HZTYOrc0+ASt7y9Wvg==;EndpointSuffix=core.windows.net";

    public static final String AZURE_CONTAINERNAME = "minimicroservice";

    public static class ImageCommon {
        public static final String AVATAR_DEFAULT = "https://minimicroservice.blob.core.windows.net/minimicroservice/defaultavatar.jpg";
    }

   public static class NumberCommon {

        public static final int ZERO = 0;
       public static final int ONE = 1;
       public static final int TWO = 2;
       public static final int THREE = 3;
        public static final int SEVEN = 7;
   }

   public static class MessageCommon {
       public static final String LOGIN_FAIL = "Login fail, please check username or password";
       public static final String USERNAME_EXIST = "Username is exist";
       public static final String EMAIL_EXIST = "Email is exist";

       public static final String DELETE_USER_SUCCESS = "Delete User success";

       public static final String VERIFY_001 = "Code has expiration";

       public static final String VERIFY_002 = "Code is wrong";
       public static final String VERIFY_003 = "Verify success";
   }

}
