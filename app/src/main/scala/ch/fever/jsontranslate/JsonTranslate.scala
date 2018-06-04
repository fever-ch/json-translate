package ch.fever.jsontranslate

import java.io._

import ch.fever.jsontranslate.translator.{GoogleTranslatorComponentImpl, TranslatorComponent}


object JsonTranslate extends App {

  case class Config(src: Option[String] = None, dst: Option[String] = None,
                    srcLang: Option[String] = None, dstLang: Option[String] = None, lstLang: Boolean = false,
                    keepOriginal: Boolean = true)


  val parser = new scopt.OptionParser[Config]("jsontranslate") {
    head("jsontranslate",BuildInfo.version)

    opt[String]('i', "input").action((x, c) =>
      c.copy(src = Some(x))).text("path of the input json file (if not specified standard input)")

    opt[String]('o', "output").action((x, c) =>
      c.copy(dst = Some(x))).text("path of the output json file (if not specified standard output)")


    opt[String]('m', "inlang").action((x, c) =>
      c.copy(srcLang = Some(x))).text("language of the input")

    opt[String]('n', "outlang").action((x, c) =>
      c.copy(dstLang = Some(x))).text("language of the output")

    opt[Boolean]('k', "keep").action((x, c) =>
      c.copy(keepOriginal = x)).text("keep entries with the original text")

    opt[Unit]('l', "list").action((_, c) =>
      c.copy(lstLang = true)).text("list available languages")

    {
      opt[Unit]('h', "help") action { (_, c) =>
        showUsage()
        terminate(Right(()))
        c
      }
    }.text("prints this usage text")


    note("\n-l or --list cannot be used with other arguments/parameters")
  }

  parser.parse(args, Config()).foreach(
    config ⇒ {
      val context: JsonTranslatorComponent with TranslatorComponent = new JsonTranslatorComponentJson4sImpl with GoogleTranslatorComponentImpl

      config match {
        case Config(None, None, None, None, true, _) ⇒
          println(context.translator.langSupport.map(l ⇒ s"${l._1}\t${l._2}").mkString("\n"))

        case Config(src, dst, srcLang, dstLang, false, keepOriginal) ⇒ {
          (srcLang, dstLang) match {
            case (Some(sourceLanguage), Some(destLanguage)) ⇒
              val in = src.map(new FileInputStream(_)).getOrElse(System.in)
              val out = dst.map(new FileOutputStream(_)).getOrElse(System.out)

              context.jsonTranslator.translate(
                new InputStreamReader(in), new OutputStreamWriter(out),
                sourceLanguage, destLanguage,
                config.keepOriginal)
            case _ ⇒
              println("Input and output languages should be specified\n")
              parser.showUsage()
          }

        }
        case _ ⇒
          parser.showUsage()
      }
    }
  )
}