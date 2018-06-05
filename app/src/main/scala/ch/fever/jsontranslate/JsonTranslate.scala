package ch.fever.jsontranslate

import java.io._

import ch.fever.jsontranslate.translator.{GoogleTranslatorComponentImpl, TranslatorComponent}


object JsonTranslate extends App {


  val parser = new scopt.OptionParser[Config]("jsontranslate") {
    head("jsontranslate", BuildInfo.version)

    cmd("translate").action((_, c) ⇒ c.copy(specificConfig = TranslateConfig())).
      text("translate a JSON file").
      children(
        opt[String]('i', "input").action((x, c) ⇒
          c.copy(specificConfig = c.specificConfig.asInstanceOf[TranslateConfig].copy(src = Some(x)))).
          text("path of the input json file, if not specified uses the standard input"),

        opt[String]('o', "output").action((x, c) ⇒
          c.copy(specificConfig = c.specificConfig.asInstanceOf[TranslateConfig].copy(dst = Some(x)))).
          text("path of the output json file, if not specified uses the standard output"),

        opt[String]('m', "inlang").required().action((x, c) ⇒
          c.copy(specificConfig = c.specificConfig.asInstanceOf[TranslateConfig].copy(srcLang = x))).
          text("language of the input"),

        opt[String]('n', "outlang").required().action((x, c) ⇒
          c.copy(specificConfig = c.specificConfig.asInstanceOf[TranslateConfig].copy(dstLang = x))).
          text("language of the output"),

        opt[Boolean]('k', "keep").action((x, c) ⇒
          c.copy(specificConfig = c.specificConfig.asInstanceOf[TranslateConfig].copy(keepOriginal = x))).
          text("keep original text in the translated JSON"),
      )

    cmd("forecast").action((_, c) => c.copy(specificConfig = ForecastConfig())).
      text("computes the number of characters that needs to be translated").
      children(
        opt[String]('i', "input").action((x, c) ⇒
          c.copy(specificConfig = c.specificConfig.asInstanceOf[ForecastConfig].copy(src = Some(x)))).
          text("path of the input json file (if not specified standard input)")
      )

    cmd("list").action((_, c) ⇒ c.copy(specificConfig = ListConfig())).
      text("list available languages")

    cmd("help").action((_, c) ⇒ c.copy(specificConfig = HelpConfig())).
      text("prints this usage text")
  }


  parser.parse(args, Config()).foreach(
    config ⇒ {
      val context: JsonTranslatorComponent with TranslatorComponent = new JsonTranslatorComponentJson4sImpl with GoogleTranslatorComponentImpl

      config match {
        case Config(ListConfig()) ⇒ListLanguages()

        case Config(forecastConfig: ForecastConfig) ⇒ Forecast(forecastConfig)

        case Config(translateConfig:TranslateConfig) ⇒ Translate(translateConfig)

        case _ ⇒
          parser.showUsage()
      }
    }
  )
}

