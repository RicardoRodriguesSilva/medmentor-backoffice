package br.com.medmentor.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

import br.com.medmentor.enums.TipoSolicitacaoAcesso;

public class EmailGenerator {

    public boolean generateEmail(String emailOrigem, String emailHost, String emailPorta,
    								String emailSenha, String emailDestino, String nomePessoa, 
    								String nomeUsuario, String novaSenha, TipoSolicitacaoAcesso tipoSolicitacaoAcesso) {
        
        Properties properties = new Properties();
        properties.put("mail.smtp.host", emailHost);
        properties.put("mail.smtp.port", emailPorta); 
        properties.put("mail.smtp.auth", "true"); 
        properties.put("mail.smtp.starttls.enable", "true");  

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailOrigem, emailSenha);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(emailOrigem));

            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(emailDestino)
            );
            
            String mensagemPorTipo = "";
            if (tipoSolicitacaoAcesso == TipoSolicitacaoAcesso.NOVA_SENHA) {
            	message.setSubject("Bem-vindo(a), " + nomePessoa + "! Seu acesso foi criado.");
            	mensagemPorTipo = "\n\nSeu acesso foi criado com sucesso";
            } else {
            	message.setSubject("Ol� " + nomePessoa + "! Seu acesso foi recuperado.");
            	mensagemPorTipo = "\n\nSeu acesso foi recuperado com sucesso";
            }

            String emailContent = "Ol�, " + nomePessoa + ","
            					+ "\n"
            					+ "\n"
                                + mensagemPorTipo
                                + "\n\nSeu login �: " + nomeUsuario 
                                + "\nSua senha �: " + novaSenha
                                + "\n\nPor favor, utilize esta senha para o seu acesso e recomendamos que n�o passe para ningu�m."
                                + "\n"
                                + "\n\nAtenciosamente,"
                                + "\n"
                                + "\n"
                                + "\nMedmentor";

            message.setText(emailContent); 

            Transport.send(message);

            System.out.println("E-mail enviado com sucesso para: " + emailDestino);
            return true;

        } catch (MessagingException e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}