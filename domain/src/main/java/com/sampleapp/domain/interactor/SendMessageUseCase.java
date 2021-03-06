package com.sampleapp.domain.interactor;

import com.sampleapp.domain.data.executor.PostExecutionThread;
import com.sampleapp.domain.data.executor.WorkExecutionThread;
import com.sampleapp.domain.model.EventModel;
import com.sampleapp.domain.model.Message;
import com.sampleapp.domain.repository.ChatRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;


public class SendMessageUseCase extends UseCase<EventModel, SendMessageUseCase.Parameters>{

    private final ChatRepository chatRepository;

    @Inject
    protected SendMessageUseCase(ChatRepository chatRepository, WorkExecutionThread threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.chatRepository = chatRepository;
    }

    @Override
    protected Flowable<EventModel> buildUseCaseObservable(Parameters params) {
        return chatRepository.sendMessage(params.message);
    }

    public static class Parameters{

        private Message message;
        private Parameters(Message message){
            this.message = message;
        }

        public static Parameters create(Message message){
            return new Parameters(message);
        }

    }

    public static SendMessageUseCase mock(ChatRepository chatRepository, WorkExecutionThread threadExecutor, PostExecutionThread postExecutionThread){
        return new SendMessageUseCase(chatRepository, threadExecutor, postExecutionThread);
    }
}
