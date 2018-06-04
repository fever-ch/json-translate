package ch.fever.jsontranslate

class JsonTranslatorComponentJson4sImpltTest extends JsonTranslatorComponentTest  {
  override def translatorComponent = new JsonTranslatorComponentJson4sImpl with TestTranslatorComponent
}