package com.example.jblog.post.vote;

import com.example.jblog.filter.dto.CustomResponse;
import com.example.jblog.post.vote.dto.VoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vote")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Integer> vote(@RequestBody VoteDto voteDto) {
        return new ResponseEntity<>(voteService.vote(voteDto), HttpStatus.OK);
    }
}
