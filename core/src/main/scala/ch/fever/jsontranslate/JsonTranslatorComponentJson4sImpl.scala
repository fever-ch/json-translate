package ch.fever.jsontranslate

import java.io

import ch.fever.jsontranslate.translator.TranslatorComponent
import org.json4s._
import org.json4s.jackson.JsonMethods._


trait JsonTranslatorComponentJson4sImpl extends JsonTranslatorComponent {
  this: TranslatorComponent ⇒

  def jsonTranslator = (src: io.Reader, dst: io.Writer, srcLang: String, dstLang: String, keepOriginal: Boolean) ⇒ {

    def pair[T](main: T, additional: ⇒ T) =
      if (keepOriginal)
        List(additional, main)
      else
        List(main)

    def translate(s: String) = translator.translate(s, srcLang, dstLang)

    val jsonTree = parse(src)

    def translateJsonField: JField ⇒ List[JField] = {
      case JField(key: String, JString(value)) ⇒
        pair(
          JField(key, JString(translate(value))),
          JField(key + origTag, JString(value))
        )
      case JField(key: String, JArray(values)) ⇒
        if (values.exists(_.isInstanceOf[JString]))
          pair(
            JField(key, JArray(values.map(translateJson))),
            JField(key + origTag, JArray(values))
          ) else
          List(JField(key, JArray(values.map(translateJson))))

      case JField(key: String, value: JValue) ⇒ List(JField(key, translateJson(value)))
    }

    def translateJson: JValue ⇒ JValue = {
      case JObject(l) ⇒ JObject.apply(l.flatMap(translateJsonField))
      case JString(a) ⇒ JString(translate(a))
      case JArray(a) ⇒ JArray(a.map(translateJson))
      case o: JValue ⇒ o
    }

    val translated = translateJson(jsonTree)

    dst.write(pretty(render(translated)))
    dst.close()
  }
}