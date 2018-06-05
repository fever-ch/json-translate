package ch.fever.jsontranslate

case class Config(specificConfig: SpecificConfig = HelpConfig())

sealed trait SpecificConfig

case class ListConfig() extends SpecificConfig

case class TranslateConfig(src: Option[String] = None, dst: Option[String] = None, srcLang: String = "", dstLang: String ="", lstLang: Boolean = false,
                           keepOriginal: Boolean = true) extends SpecificConfig

case class ForecastConfig(src: Option[String] = None) extends SpecificConfig

case class HelpConfig() extends SpecificConfig
