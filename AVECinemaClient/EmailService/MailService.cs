using System;
using System.IO;
using System.Net.Mail;
using Google.Apis.Gmail.v1;
using Google.Apis.Gmail.v1.Data;
using Google.Apis.Auth.OAuth2;
using Google.Apis.Services;
using Google.Apis.Util.Store;
using System.Collections.Generic;
using System.Threading;
using System.Reflection;

namespace CinemaEmailService
{
    public class MailService
    {
        private GmailService gmailService;
        public string SenderMail { get; set; }

        public MailService(string senderMail)
        {
            SenderMail = senderMail;
            gmailService = getGmailService();
        }

        public void sendEmail(string email, string subject, string body)
        {
            var msg = new AE.Net.Mail.MailMessage
            {
                Subject = subject,
                Body = body,
                From = new MailAddress(SenderMail)
            };

            msg.To.Add(new MailAddress(email));
            msg.ReplyTo.Add(msg.From); 
            var msgStr = new StringWriter();
            msg.Save(msgStr);

            
            var gmail = gmailService;
            var result = gmail.Users.Messages.Send(new Message
            {
                Raw = Base64UrlEncode(msgStr.ToString())
            }, "me").Execute();
            Console.WriteLine("Message ID {0} sent.", result.Id);
        }

        private string Base64UrlEncode(string input)
        {
            var inputBytes = System.Text.Encoding.UTF8.GetBytes(input);
            // Special "url-safe" base64 encode.
            return Convert.ToBase64String(inputBytes)
              .Replace('+', '-')
              .Replace('/', '_')
              .Replace("=", "");
        }

        private GmailService getGmailService()
        {
            // If modifying these scopes, delete your previously saved credentials
            // at ~/.credentials/gmail-dotnet-quickstart.json
            string[] Scopes = { GmailService.Scope.GmailCompose };
            string ApplicationName = "AVE Cinema Email Service";
            UserCredential credential;
            var assembly = Assembly.GetExecutingAssembly();
            var resourceName = AppDomain.CurrentDomain.BaseDirectory + "credentials.json";
            using (var stream =
                new FileStream(resourceName, FileMode.Open, FileAccess.Read))
            {
                // The file token.json stores the user's access and refresh tokens, and is created
                // automatically when the authorization flow completes for the first time.
                string credPath = AppDomain.CurrentDomain.BaseDirectory + "token.json";
                credential = GoogleWebAuthorizationBroker.AuthorizeAsync(
                    GoogleClientSecrets.Load(stream).Secrets,
                    Scopes,
                    "user",
                    CancellationToken.None,
                    new FileDataStore(credPath, true)).Result;
            }

            // Create Gmail API service.
            var service = new GmailService(new BaseClientService.Initializer()
            {
                HttpClientInitializer = credential,
                ApplicationName = ApplicationName,
            });

            return (GmailService)service;
        }
    }
}
