package sk.essentialdata.lunchmenu

/**
  * @author miso
  */
trait StringSanitizingSupport {
  implicit class RichString(string: String) {
    private val NON_ALPHA = "[^a-zA-Z]"
    def trimNonAlphabetic = string.replaceAll(s"^$NON_ALPHA+|$NON_ALPHA+$$", "")
  }

}
