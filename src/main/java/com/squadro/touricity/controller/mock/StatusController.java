package com.squadro.touricity.controller.mock;

import com.squadro.touricity.message.types.IMessage;
import com.squadro.touricity.message.types.Status;
import com.squadro.touricity.message.types.data.Stop;
import com.squadro.touricity.message.types.data.enumeration.StatusCode;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
public class StatusController {

    private class StatusArray implements IMessage {
        private Collection<Status> statusList;

        public StatusArray(Collection<Status> statusList) {
            this.statusList = statusList;
        }

        public Status[] getStatus_codes() {
            Status[] arr = new Status[statusList.size()];
            return statusList.toArray(arr);
        }
    }

    @RequestMapping(value = "/mock/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public IMessage statuses(@RequestParam(value="random", defaultValue="false") String random) {
        // random not used
        ArrayList<Status> statuses = new ArrayList<Status>();

        statuses.add(Status.build(StatusCode.SIGNIN_SUCCESSFUL));
        statuses.add(Status.build(StatusCode.SIGNIN_REJECT));
        statuses.add(Status.build(StatusCode.SIGNUP_SUCCESSFUL));
        statuses.add(Status.build(StatusCode.SIGNUP_REJECT));
        statuses.add(Status.build(StatusCode.SIGNUP_REJECT_USERNAME));
        statuses.add(Status.build(StatusCode.SIGNUP_REJECT_PASSWORD));
        statuses.add(Status.build(StatusCode.SIGNOUT_SUCCESSFULL));
        statuses.add(Status.build(StatusCode.REJECT_COOKIE_NOTSET));

        return new StatusArray(statuses);
    }
}
