### Endpoints

#### Envoyer un message

URL : POST http://localhost:8081/api/messages/send
Body (JSON) :

    {
        "senderId": "user1",
        "receiverId": "user2",
        "content": "Salut, comment vas-tu ?"
    }
