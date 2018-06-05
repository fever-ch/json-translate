package ch.fever.jsontranslate

import java.io.{FileInputStream, FileOutputStream, InputStreamReader, OutputStreamWriter}

object Translate extends TranslatorContextComponent {
  def apply(config: TranslateConfig): Unit = {
    val in = config.src.map(new FileInputStream(_)).getOrElse(System.in)
    val out = config.dst.map(new FileOutputStream(_)).getOrElse(System.out)

    context.jsonTranslator.translate(
      new InputStreamReader(in), new OutputStreamWriter(out),
      config.srcLang, config.dstLang,
      config.keepOriginal)
  }
}
