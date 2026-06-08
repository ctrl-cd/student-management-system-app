package com.studentmanagementsystem.student_management_system_app.util;

public final class Constants {

  private Constants() {
  }

  public static final class Messages {
    public static final class Success {
      public static final String SUCCESSFULLY_CREATED_THE_ENTRY = "Successfully created the entry";
      public static final String SUCCESSFULLY_CREATED_THE_ENTRIES = "Successfully created the entries";
      public static final String SUCCESSFULLY_FETCHED_THE_ENTRY = "Successfully fetched the entry";
      public static final String SUCCESSFULLY_FETCHED_THE_ENTRIES = "Successfully fetched the entries";
      public static final String SUCCESSFULLY_UPDATED_THE_ENTRY = "Successfully updated the entry";
      public static final String SUCCESSFULLY_DELETED_THE_ENTRY = "Successfully deleted the entry";
      public static final String SUCCESSFULLY_DELETED_THE_ENTRIES = "Successfully deleted the entries";
      public static final String SUCCESSFULLY_DELETED_D_ENTRIES_AND_D_ENTRIES_WERE_NOT_FOUND = "Successfully deleted %d entries, and %d entries were not found";
    }

    public static final class Error {
      public static final String ENTRY_NOT_FOUND = "Entry not found";
      public static final String ENTRY_DELETION_UNSUCCESSFUL = "Entry deletion unsuccessful";
      public static final String INTERNAL_SERVER_ERROR = "Internal server error";
      public static final String NO_RESOURCE_FOUND = "No resource found";
      public static final String NO_STATIC_RESOURCE = "No static resource";
    }

    public static final class Validation {
      public static final String THIS_FIELD_IS_REQUIRED = "This field is required";
      public static final String THIS_FIELD_MUST_BE_10_CHARACTERS = "This field must be 10 characters";

      public static final String THIS_FIELD_MUST_BE_GREATER_THAN_2_CHARACTERS = "This field must be greater than or equal to 2 characters";

      public static final String THIS_FIELD_MUST_BE_BETWEEN_6_20_CHARACTERS = "This field must be between 6 and 20 characters";
      public static final String THIS_FIELD_MUST_BE_BETWEEN_2_50_CHARACTERS = "This field must be between 2 and 50 characters";
      public static final String THIS_FIELD_MUST_BE_VALID = "This field must be valid";
      public static final String THIS_FIELD_MUST_BE_LESS_THAN_1000 = "This field must be less than 1000 characters";
      public static final String THIS_FIELD_MUST_BE_GREATER_THAN_OR_EQUAL_TO_0 = "This field must be greater than or equal to 0";
      public static final String THIS_FIELD_MUST_BE_GREATER_THAN_OR_EQUAL_TO_1 = "This field must be greater than or equal to 1";
      public static final String THIS_FIELD_IS_REQUIRED_WHEN_OTHER_FIELDS_ARE_NULL = "This field is required when other fields are null";
      public static final String THIS_FIELD_IS_REQUIRED_WHEN_ID_IS_PRESENT = "This field is required when id is present";
      public static final String IDS_ARE_REQUIRED = "Ids are required";
      public static final String PLEASE_PROVIDE_A_DATA_TO_PROCESS = "Please provide a data to process";
      public static final String INVALID_CREDENTIALS = "Invalid Credentials";
      public static final String INVALID_OR_MISSING_ACCESS_TOKEN = "Invalid or missing access token";
      public static final String INVALID_OR_MISSING_REFRESH_TOKEN = "Invalid or missing refresh token";
      public static final String INVALID_OR_MISSING_TOKEN = "Invalid or missing token";
      public static final String INVALID_CORS_REQUEST = "Invalid CORS request";
      public static final String AT_LEAST_ONE_ROLE_IS_REQUIRED = "At least one role is required";
      public static final String VALIDATION_FAILED = "Validation failed";
      public static final String ACCESS_DENIED_INSUFFICIENT_ROLE_PRIVILEGES = "Access denied. Insufficient role privileges";
      public static final String ACCESS_DENIED_UPDATE_ALLOWED_ONLY_FOR_OWN_ENTRY = "Access denied. Update allowed only for own entry";
      public static final String ACCESS_DENIED_DELETE_ALLOWED_ONLY_FOR_OWN_ENTRY = "Access denied. Delete allowed only for own entry";
      public static final String INVALID_URL = "Invalid URL";
      //
      public static final String ENTRY_ALREADY_EXISTS = "Entry already exists";
      public static final String DUPLICATE_ENTRY_FOUND = "Duplicate entry found";
    }
  }

  public static final class DateFormats {
    public static final String YYYY_MM_DD_T_HH_MM_SS_Z = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
  }

  public static final class Exceptions {
    public static final String DUPLICATE_ENTRY_S_FOR_KEY_S = "Duplicate entry '%s' for key '%s'";
  }

  public static final class Headers {
    public static final String CONTENT_TYPE_APPLICATION_JSON = "Content-Type=application/json";
    public static final String ACCEPT_APPLICATION_JSON = "Accept=application/json";
  }

  public static final class TimezoneType {
    public static final String UTC = "UTC";
  }
}
