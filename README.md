# AndroidStudio-Resource-Remover
Generate the lint xml file then use this tool to remove all the unused resources.This is currently limited to single file deletion.

Expected problem element in the lint generated xml is 


//<problem>
//    <file>file://$PROJECT_DIR$/schoolEraGlobal/src/main/res/values/color.xml</file>
//    <line>25</line>
//    <module>schoolEraGlobal</module>
//    <entry_point TYPE="file" FQNAME="file://$PROJECT_DIR$/schoolEraGlobal/src/main/res/values/color.xml" />
//    <problem_class severity="WARNING" attribute_key="WARNING_ATTRIBUTES">Unused resources</problem_class>
//    <hints />
//    <description>&lt;html&gt;The resource &lt;code&gt;R.color.ButtonColorWhenPrimaryIsOrange&lt;/code&gt; appears to be unused&lt;/html&gt;</description>
//  </problem>

