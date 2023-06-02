<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" indent="yes" />

<xsl:template match="/">
   <html>
   <p><b><xsl:value-of select="//id_document" /></b></p>
   <p><b>Info: </b><xsl:value-of select="//name" /></p>
   <p><b>Doctors:</b></p>
   <table border="1">
      <th>Patient</th>
      <th>Id document</th>
      <th>Name</th>
      <th>Surname</th>
      <xsl:for-each select="Doctor/Patients/Patient">
      <xsl:sort select="@name" />
         <xsl:if test="starts-with(name,'C')">
            <tr>
            <td><i><xsl:value-of select="@name" /></i></td>
            <td><xsl:value-of select="id_document" /></td>
            <td><xsl:value-of select="name" /></td>
            <td><xsl:value-of select="surname" /></td>
            <td><xsl:value-of select="email" /></td>
            </tr>
         </xsl:if>
      </xsl:for-each>
   </table>
   </html>
</xsl:template>

</xsl:stylesheet>