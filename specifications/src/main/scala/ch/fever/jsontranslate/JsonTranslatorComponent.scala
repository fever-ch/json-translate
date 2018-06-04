package ch.fever.jsontranslate

import java.io
import java.io.{StringReader, StringWriter}


trait JsonTranslatorComponent {

  def jsonTranslator: JsonTranslator

  val origTag = "_jt_orig"

  trait JsonTranslator {
    def translate(src: io.Reader, dst: io.Writer, srcLang: String, dstLang: String, keepOriginal: Boolean)

    def translateSrc(src: String, srcLang: String, dstLang: String, keepOriginal: Boolean) = {
      val sw = new StringWriter()
      translate(new StringReader(src), sw, srcLang, dstLang, keepOriginal)
      sw.getBuffer.toString
    }
  }

}