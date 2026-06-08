package com.studentmanagementsystem.student_management_system_app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class Utils {

  private static final boolean isDisableSystemOutPrintMethod = false;
  private static final String ANSI_WHITE_TEXT = "\u001B[37m";
  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_BRIGHT_CRAYON_TEXT = "\u001B[96m";
  private static final String ANSI_BLACK_TEXT_YELLOW_BACKGROUND = "\u001B[30;43m";
  private static final String ANSI_ITALIC_START = "\033[3m";
  private static final String ANSI_ITALIC_END = "\033[0m";

  private static final PrintStream originalOut = System.out;

  private static final ObjectMapper objectMapper = new ObjectMapper();
  private static Environment environment;

  public Utils(Environment environment) {
    Utils.environment = environment;
  }

  public static String getDateTimeLocalCurrent(String format) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
    return LocalDateTime.now().format(dateTimeFormatter);
  }

  public static StackWalker.StackFrame getCurrentStackFrame() {
    return getCurrentStackFrame(0);
  }

  public static StackWalker.StackFrame getCurrentStackFrame(long skipFrame) {
    return StackWalker.getInstance().walk(frames -> frames.skip(skipFrame).findFirst().orElse(null));
  }

  private static String objectFormat(Object obj) {
    if (obj == null) {
      return "null";
    }
    else if (obj instanceof String) {
      return "\"" + obj + "\"";
    }
    else if (obj instanceof int[]) {
      return Arrays.toString((int[]) obj);
    }
    else if (obj instanceof double[]) {
      return Arrays.toString((double[]) obj);
    }
    else if (obj instanceof float[]) {
      return Arrays.toString((float[]) obj);
    }
    else if (obj instanceof long[]) {
      return Arrays.toString((long[]) obj);
    }
    else if (obj instanceof short[]) {
      return Arrays.toString((short[]) obj);
    }
    else if (obj instanceof byte[]) {
      return Arrays.toString((byte[]) obj);
    }
    else if (obj instanceof char[]) {
      return Arrays.toString((char[]) obj);
    }
    else if (obj instanceof boolean[]) {
      return Arrays.toString((boolean[]) obj);
    }
    else if (obj instanceof Object[]) {
      // Recursively format object arrays
      return Arrays.deepToString(Arrays.stream((Object[]) obj).map(Utils::objectFormat).toArray());
    }
    else if (obj instanceof Map) {
      Map<?, ?> map = (Map<?, ?>) obj;
      StringBuilder sb = new StringBuilder("{");
      for (Map.Entry<?, ?> entry : map.entrySet()) {
        sb.append(objectFormat(entry.getKey())).append(": ").append(objectFormat(entry.getValue())).append(", ");
      }
      if (!map.isEmpty())
        sb.setLength(sb.length() - 2); // Remove last comma
      sb.append("}");
      return sb.toString();
    }
    else if (obj instanceof Collection) {
      // Recursively format collections (List, Set, etc.)
      Collection<?> collection = (Collection<?>) obj;
      return "[" + collection.stream().map(Utils::objectFormat).toList() + "]";
    }
    else if (obj.getClass() == Object.class) {
      // Explicitly empty object
      return "{}";
    }
    return obj.toString(); // Default case for non-collections
  }

  @SafeVarargs
  public static <T> void systemOutPrint(String format, T... args) {
    //    Utils.systemOutPrint("anyData *****", anyData);
    if (isDisableSystemOutPrintMethod) {
      return;
    }

    String datePrefix = getDateTimeLocalCurrent("hh:mm:ss.SSSSSSSSS a") + " => ";
    System.out.println("\n" + Utils.getCurrentStackFrame(2));

    //    format = format.trim();
    int lastSpaceIndex = format.lastIndexOf(' ');

    //    String format1 = format.substring(0, format.length() - 6).trim();
    //    String format2 = format.substring(format.length() - 6);
    String format1 = format.substring(0, lastSpaceIndex);
    String format2 = format.substring(lastSpaceIndex + 1);

    String datePrefixColored = ANSI_WHITE_TEXT + datePrefix + ANSI_RESET;
    String formatColored = ANSI_BLACK_TEXT_YELLOW_BACKGROUND + " " + format1 + " " + ANSI_RESET;

    //    System.out.printf("🚀 ");
    //    System.out.printf("😄 ");
    //    System.out.printf("😊 ");
    //    System.out.print("😊 ");
    System.out.print("🙂 ");

    if (args == null) {
      // System.out.printf(Locale.getDefault(), datePrefixColored + formatColored + " %s \n", format2);
      System.out.printf(Locale.getDefault(), datePrefixColored + formatColored + "%s \n", format2);
      return;
    }

    if (args.length == 1) {
      //      System.out.printf(Locale.getDefault(), datePrefixColored + formatColored + " %s %s\n", format2, ANSI_BRIGHT_GREEN_TEXT + objectFormat(args[0]) + ANSI_RESET);
      //      System.out.printf(Locale.getDefault(), datePrefixColored + formatColored + " %s %s\n", format2, ANSI_BRIGHT_CRAYON_TEXT + objectFormat(args[0]) + ANSI_RESET);
      // System.out.printf(Locale.getDefault(), datePrefixColored + formatColored + " %s " + ANSI_ITALIC_START + "%s" + ANSI_ITALIC_END + "\n", format2, ANSI_BRIGHT_CRAYON_TEXT + objectFormat(args[0]) + ANSI_RESET);
      System.out.printf(Locale.getDefault(), datePrefixColored + formatColored + "%s " + ANSI_ITALIC_START + "%s" + ANSI_ITALIC_END + "\n", format2,
                        ANSI_BRIGHT_CRAYON_TEXT + objectFormat(args[0]) + ANSI_RESET);
    }
    else {
      //      System.out.printf(Locale.getDefault(), datePrefixColored + formatColored + " %s %s\n", format2, ANSI_BRIGHT_GREEN_TEXT + Arrays.deepToString(Arrays.stream(args).map(Utils::objectFormat).toArray()) + ANSI_RESET);
      // System.out.printf(Locale.getDefault(), datePrefixColored + formatColored + " %s " + ANSI_ITALIC_START + "%s" + ANSI_ITALIC_END + "\n", format2, ANSI_BRIGHT_CRAYON_TEXT + Arrays.deepToString(Arrays.stream(args).map(Utils::objectFormat).toArray()) + ANSI_RESET);
      System.out.printf(Locale.getDefault(), datePrefixColored + formatColored + "%s " + ANSI_ITALIC_START + "%s" + ANSI_ITALIC_END + "\n", format2,
                        ANSI_BRIGHT_CRAYON_TEXT + Arrays.deepToString(Arrays.stream(args).map(Utils::objectFormat).toArray()) + ANSI_RESET);
    }
  }

  public static void disableAllSystemOut(boolean isDisable) {
    if (isDisable) {
      //      systemOutPrint("Disabled all System.out System.err");
      systemOutPrint("Disabled all System.out");

      //      System.setOut(new PrintStream(NULL_OUTPUT_STREAM));
      //      System.setErr(new PrintStream(NULL_OUTPUT_STREAM));

      System.setOut(new PrintStream(OutputStream.nullOutputStream()));
      //      System.setErr(new PrintStream(OutputStream.nullOutputStream()));
    }
    else {
      System.setOut(originalOut);
      //      System.setErr(originalErr);
    }
  }

  public static boolean isDev() {
    return environment.acceptsProfiles(Profiles.of("dev"));
  }

  public static boolean isProd() {
    return environment.acceptsProfiles(Profiles.of("prod"));
  }

  public static Sort sortParamsParse(List<String> sortParams) {
    if (sortParams == null || sortParams.isEmpty()) {
      return Sort.unsorted();
    }

    List<Sort.Order> orders = new ArrayList<>();
    int i = 0;
    while (i < sortParams.size()) {
      String param = sortParams.get(i);

      // Case: "createdAt,asc"
      if (param.contains(",")) {
        String[] parts = param.split(",");
        String property = parts[0];
        Sort.Direction direction = (parts.length > 1 && parts[1].equalsIgnoreCase("desc")) ? Sort.Direction.DESC : Sort.Direction.ASC;
        orders.add(new Sort.Order(direction, property));
        i++;
      }
      // Case: ["createdAt", "asc"]
      else if (i + 1 < sortParams.size() && (sortParams.get(i + 1).equalsIgnoreCase("asc") || sortParams.get(i + 1).equalsIgnoreCase("desc"))) {
        String property = param;
        String dir = sortParams.get(i + 1);
        Sort.Direction direction = dir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        orders.add(new Sort.Order(direction, property));
        i += 2;
      }
      // Case: ["createdAt"]
      else {
        orders.add(new Sort.Order(Sort.Direction.ASC, param));
        i++;
      }
    }
    return Sort.by(orders);
  }

  public static String toJsonStringFromObject(Object obj) {
    if (obj == null) {
      return null;
    }
    try {
      return objectMapper.writeValueAsString(obj);
    }
    catch (JsonProcessingException jsonProcessingException) {
      Utils.systemOutPrint("jsonProcessingException *****", jsonProcessingException);
      systemOutPrint("jsonProcessingException - ", jsonProcessingException);
      return null;
    }
  }

  public static <T> Set<T> getDuplicateElements(List<T> elements) {
    Set<T> seen = new HashSet<>();
    Set<T> duplicateElements = new HashSet<>();

    if (elements == null) {
      return duplicateElements; // return empty set
    }

    for (T element : elements) {
      if (!seen.add(element)) {
        duplicateElements.add(element);
      }
    }
    return duplicateElements;
  }

  public static String getIpAddressFromHttpServletRequest(HttpServletRequest httpServletRequest) {
    if (httpServletRequest == null) {
      return null;
    }
    /*String ipAddress = httpServletRequest.getHeader("X-Forwarded-For");
    if (ipAddress == null || ipAddress.isEmpty()) {
      ipAddress = httpServletRequest.getRemoteAddr();
    }
    return ipAddress;*/

    String[] IP_HEADERS = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA"};

    for (String header : IP_HEADERS) {
      String ipAddress = httpServletRequest.getHeader(header);
      if (ipAddress != null && !ipAddress.isBlank() && !"unknown".equalsIgnoreCase(ipAddress)) {
        int commaIndex = ipAddress.indexOf(',');
        if (commaIndex > 0) {
          ipAddress = ipAddress.substring(0, commaIndex).trim();
        }
        return ipAddress;
      }
    }
    return httpServletRequest.getRemoteAddr();
  }

  public static String getDeviceTypeFromHttpServletRequest(HttpServletRequest httpServletRequest) {
    String deviceType = "Unknown";

    if (httpServletRequest == null) {
      return deviceType;
    }

    String userAgentString = httpServletRequest.getHeader("User-Agent");
    if (userAgentString == null || userAgentString.isEmpty()) {
      return deviceType;
    }

    try {
      UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
      DeviceType deviceTypeObj = userAgent.getOperatingSystem().getDeviceType();
      deviceType = deviceTypeObj != null ? deviceTypeObj.getName() : deviceType;
      return deviceType;
    }
    catch (Exception exception) {
      return deviceType;
    }
  }

  public static String getUserAgentFromHttpServletRequest(HttpServletRequest httpServletRequest) {
    if (httpServletRequest == null) {
      return "Unknown";
    }

    String userAgent = httpServletRequest.getHeader("User-Agent");
    if (userAgent == null || userAgent.isEmpty()) {
      return "Unknown";
    }
    return userAgent;
  }

  public static String generateStudentCodeUnique() {
    String PREFIX = "STU";
    int currentYear = LocalDate.now().getYear();
    String studentCodeUnique = PREFIX + "-" + currentYear + "-" + System.currentTimeMillis();
    return studentCodeUnique;
  }
}
