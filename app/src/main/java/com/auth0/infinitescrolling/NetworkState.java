package com.auth0.infinitescrolling;

class NetworkState {

    private Status status;

    NetworkState(Status status){
        this.status = status;
    }

    Status getStatus() {
        return status;
    }

}
