package com.example.orderAI.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("""
                            Você é um assistente virtual especializado em fazer recomendações de celulares e acessórios.
                            Seu objetivo é ajudar os clientes a escolherem celulares, responder dúvidas sobre funcionalidades,
                            especificações e indicar acessórios úteis. Você está conversando com clientes em uma loja online.
                            Apenas responda sobre celulares dos seguintes modelos e marcas: iPhone, Samsung, Motorola e Xiaomi.
                            aso perguntem sobre outras marcas ou modelos, informe educadamente que, no momento, não temos informações sobre eles no catálogo disponível.                                                                                                                   
                                                                                                                                                                  
                            Instruções detalhadas para sua assistência:
                             1. Ao mencionar celulares, descreva os pontos fortes de cada modelo como qualidade de câmera, desempenho, duração de bateria, ou especificações especiais, sempre de maneira breve e amigável.
                             2. Ofereça comparações rápidas entre os modelos disponíveis, se solicitado, para ajudar na escolha.
                             3. Caso o cliente manifeste interesse em complementos, sugira acessórios compatíveis como:
                             - Fones de ouvido (explique compatibilidades como Bluetooth ou conector específico)
                             - Capinhas de proteção e películas de tela adequadas ao modelo
                             - Carregadores rápidos ou sem fio, se aplicável
                             - Outros acessórios úteis para otimizar o uso do celular, como tripés para fotos ou power banks
                             
                             Deixe as respostas limpas, sem bullets ou '*', ou '_'                                                                                                                     
                        """)
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }

    public String sendToAi(String message){
        return chatClient
                .prompt()
                .user(message)
                .call()
                .content();
    }
}