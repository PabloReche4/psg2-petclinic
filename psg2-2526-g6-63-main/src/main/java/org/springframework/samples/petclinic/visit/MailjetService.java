package org.springframework.samples.petclinic.visit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
 
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
 
@Service
public class MailjetService {
 
    @Value("${mailjet.api-key}")
    private String apiKey;
 
    @Value("${mailjet.secret-key}")
    private String secretKey;
 
    public void enviarRecordatorio(String emailDestino, String nombreMascota,
                               String fechaVisita) {

    String htmlBody = buildEmailHtml(nombreMascota, fechaVisita);

    // Escapamos las comillas dobles del HTML para meterlo en el JSON
    String htmlEscapado = htmlBody.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "")
            .replace("\r", "");

    String cuerpoJson = """
            {
              "Messages": [{
                "From": {
                  "Email": "petclinicpsg@gmail.com",
                  "Name": "Tu clínica veterinaria de confianza"
                },
                "To": [{
                  "Email": "%s"
                }],
                "Subject": "Recordatorio: cita de %s 🐾",
                "TextPart": "Hola! Te recordamos que %s tiene una cita el %s. ¡Hasta pronto!",
                "HTMLPart": "%s"
              }]
            }
            """.formatted(
                    emailDestino,
                    nombreMascota,
                    nombreMascota,
                    fechaVisita,
                    htmlEscapado
            );

    String credenciales = apiKey + ":" + secretKey;
    String autenticacion = "Basic " + Base64.getEncoder().encodeToString(credenciales.getBytes());

    HttpRequest peticion = HttpRequest.newBuilder()
            .uri(URI.create("https://api.mailjet.com/v3.1/send"))
            .header("Authorization", autenticacion)
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(cuerpoJson))
            .build();

    try {
        HttpResponse<String> respuesta = HttpClient.newHttpClient()
                .send(peticion, HttpResponse.BodyHandlers.ofString());

        if (respuesta.statusCode() == 200 || respuesta.statusCode() == 201) {
            System.out.println("Email enviado correctamente a " + emailDestino);
        } else {
            System.out.println("Error al enviar email. Código: " + respuesta.statusCode());
            System.out.println("Respuesta de Mailjet: " + respuesta.body());
        }
    } catch (Exception e) {
        System.out.println("Excepción al llamar a Mailjet: " + e.getMessage());
    }
}

private String buildEmailHtml(String nombreMascota, String fechaVisita) {

    String fecha = fechaVisita;
    String hora = "";

    if (fechaVisita != null && fechaVisita.contains(" ")) {
        String[] partes = fechaVisita.split(" ");
        fecha = partes[0];
        hora = partes[1];
    }

    return """
    <!DOCTYPE html>
    <html lang="es">
    <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <title>Recordatorio de cita</title>
    </head>

    <body style="margin:0;padding:0;background-color:#f5f0eb;font-family:Arial,Helvetica,sans-serif;">

      <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f5f0eb;padding:32px 16px;">
        <tr>
          <td align="center">

            <table width="560" cellpadding="0" cellspacing="0"
                   style="max-width:560px;width:100%%;border-radius:16px;overflow:hidden;border:1px solid #ddd8d0;">

              <!-- HEADER -->
              <tr>
                <td style="background-color:#1a3a2e;padding:36px 40px 28px;text-align:center;">

                  <table cellpadding="0" cellspacing="0" align="center" style="margin-bottom:20px;">
                    <tr>
                      <td style="background-color:#4caf81;width:38px;height:38px;border-radius:50%%;text-align:center;">
                        <span style="color:white;font-size:20px;line-height:38px;">🏥</span>
                      </td>

                      <td style="padding-left:10px;">
                        <span style="color:white;font-size:17px;font-weight:bold;">PetClinic</span>
                      </td>
                    </tr>
                  </table>

                  <table cellpadding="0" cellspacing="0" align="center" style="margin-bottom:16px;">
                    <tr>
                      <td style="background-color:#4caf81;width:64px;height:64px;border-radius:50%%;text-align:center;">
                        <span style="font-size:30px;line-height:64px;">📅</span>
                      </td>
                    </tr>
                  </table>

                  <h1 style="color:white;margin:0 0 6px;font-size:24px;font-weight:bold;">
                    Recordatorio de cita
                  </h1>

                  <p style="color:#9ecfb4;margin:0;font-size:14px;">
                    Tu mascota tiene una visita próximamente
                  </p>

                </td>
              </tr>

              <!-- BODY -->
              <tr>
                <td style="background-color:#f5f0eb;padding:32px 40px;">

                  <p style="color:#3a3a3a;font-size:15px;margin:0 0 24px;line-height:1.6;">
                    ¡Hola! Te recordamos que <strong>%s</strong>
                    tiene una cita veterinaria programada.
                  </p>

                  <!-- DETALLES -->
                  <table width="100%%" cellpadding="0" cellspacing="0"
                         style="background-color:white;border-radius:12px;border:1px solid #ddd8d0;margin-bottom:24px;">

                    <tr>
                      <td style="padding:20px 24px;">

                        <table width="100%%" cellpadding="0" cellspacing="0">
                          <tr>

                            <!-- FECHA -->
                            <td width="50%%" style="vertical-align:top;padding-right:8px;">

                              <table cellpadding="0" cellspacing="0">
                                <tr>

                                  <td style="background-color:#e8f5ee;width:34px;height:34px;border-radius:8px;text-align:center;">
                                    <span style="font-size:16px;">📆</span>
                                  </td>

                                  <td style="padding-left:10px;">
                                    <p style="margin:0;font-size:11px;color:#888888;">Fecha</p>

                                    <p style="margin:0;font-size:14px;font-weight:bold;color:#1a1a1a;">
                                      %s
                                    </p>
                                  </td>

                                </tr>
                              </table>

                            </td>

                            <!-- HORA -->
                            <td width="50%%" style="vertical-align:top;padding-left:8px;">

                              <table cellpadding="0" cellspacing="0">
                                <tr>

                                  <td style="background-color:#e8f5ee;width:34px;height:34px;border-radius:8px;text-align:center;">
                                    <span style="font-size:16px;">🕙</span>
                                  </td>

                                  <td style="padding-left:10px;">
                                    <p style="margin:0;font-size:11px;color:#888888;">Hora</p>

                                    <p style="margin:0;font-size:14px;font-weight:bold;color:#1a1a1a;">
                                      %s
                                    </p>
                                  </td>

                                </tr>
                              </table>

                            </td>

                          </tr>
                        </table>

                      </td>
                    </tr>

                  </table>

                  <!-- AVISO -->
                  <table width="100%%" cellpadding="0" cellspacing="0"
                         style="background-color:#fff8e6;border-radius:10px;border:1px solid #f0d88a;margin-bottom:28px;">

                    <tr>
                      <td style="padding:14px 18px;">

                        <p style="margin:0;font-size:13px;color:#7a5c00;line-height:1.5;">
                          Recuerda traer la cartilla sanitaria de
                          <strong>%s</strong>
                          y llegar 5 minutos antes de tu cita.
                        </p>

                      </td>
                    </tr>

                  </table>

                </td>
              </tr>

              <!-- FOOTER -->
              <tr>
                <td style="background-color:#1a3a2e;padding:22px 40px;text-align:center;">

                  <p style="color:#9ecfb4;margin:0 0 4px;font-size:13px;">
                    Tu clínica veterinaria de confianza
                  </p>

                  <p style="color:#5a8a72;margin:0;font-size:12px;">
                    petclinicpsg@gmail.com · No respondas a este correo
                  </p>

                </td>
              </tr>

            </table>

          </td>
        </tr>
      </table>

    </body>
    </html>
    """.formatted(nombreMascota, fecha, hora, nombreMascota);
} 
}