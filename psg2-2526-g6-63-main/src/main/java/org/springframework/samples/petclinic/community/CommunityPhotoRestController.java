package org.springframework.samples.petclinic.community;

import java.time.YearMonth;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.auth.payload.response.MessageResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/community")
@Tag(name = "Community Photos", description = "Community photo API")
@SecurityRequirement(name = "bearerAuth")
public class CommunityPhotoRestController {

    private final CommunityPhotoService communityPhotoService;

    public CommunityPhotoRestController(CommunityPhotoService communityPhotoService) {
        this.communityPhotoService = communityPhotoService;
    }

    @GetMapping("/photos")
    public ResponseEntity<List<CommunityPhotoResponse>> listPhotos(
        @RequestParam(value = "limit", required = false, defaultValue = "20") int limit
    ) {
        return new ResponseEntity<>(communityPhotoService.findApproved(limit), HttpStatus.OK);
    }

    @GetMapping("/photos/pending")
    public ResponseEntity<List<CommunityPhotoResponse>> listPendingPhotos(
        @RequestParam(value = "limit", required = false, defaultValue = "50") int limit
    ) {
        return new ResponseEntity<>(communityPhotoService.findPending(limit), HttpStatus.OK);
    }

    @GetMapping("/photos/mine")
    public ResponseEntity<List<CommunityPhotoResponse>> listMyPhotos() {
        return new ResponseEntity<>(communityPhotoService.findMine(), HttpStatus.OK);
    }

    @PostMapping("/photos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommunityPhotoResponse> createPhoto(
        @RequestBody @Valid CommunityPhotoRequest request
    ) {
        return new ResponseEntity<>(communityPhotoService.createPhoto(request), HttpStatus.CREATED);
    }

    @PostMapping(path = "/photos/upload", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommunityPhotoResponse> uploadPhoto(
        @RequestParam("file") MultipartFile file,
        @RequestParam(value = "caption", required = false) String caption,
        @RequestParam(value = "petId", required = false) Integer petId
    ) {
        return new ResponseEntity<>(
            communityPhotoService.createPhotoFromUpload(file, caption, petId),
            HttpStatus.CREATED
        );
    }

    @PutMapping("/photos/{photoId}")
    public ResponseEntity<CommunityPhotoResponse> updatePhoto(
        @PathVariable("photoId") int photoId,
        @RequestBody @Valid CommunityPhotoRequest request
    ) {
        return new ResponseEntity<>(communityPhotoService.updatePhoto(photoId, request), HttpStatus.OK);
    }

    @DeleteMapping("/photos/{photoId}")
    public ResponseEntity<MessageResponse> deletePhoto(@PathVariable("photoId") int photoId) {
        communityPhotoService.deletePhoto(photoId);
        return new ResponseEntity<>(new MessageResponse("Photo deleted"), HttpStatus.OK);
    }

    @PostMapping("/photos/{photoId}/votes")
    public ResponseEntity<MessageResponse> votePhoto(@PathVariable("photoId") int photoId) {
        communityPhotoService.votePhoto(photoId);
        return new ResponseEntity<>(new MessageResponse("Vote registered"), HttpStatus.OK);
    }

    @GetMapping("/photos/{photoId}/votes")
    public ResponseEntity<CommunityPhotoResponse> getVotes(@PathVariable("photoId") int photoId) {
        return new ResponseEntity<>(communityPhotoService.findVotes(photoId), HttpStatus.OK);
    }

    @PutMapping("/photos/{photoId}/status")
    public ResponseEntity<CommunityPhotoResponse> updateStatus(
        @PathVariable("photoId") int photoId,
        @RequestParam("status") CommunityPhotoStatus status
    ) {
        return new ResponseEntity<>(communityPhotoService.updateStatus(photoId, status), HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity<List<CommunityPhotoResponse>> getTop(
        @RequestParam(value = "month", required = false) String month,
        @RequestParam(value = "limit", required = false, defaultValue = "10") int limit
    ) {
        YearMonth target = month != null ? YearMonth.parse(month) : YearMonth.now();
        return new ResponseEntity<>(communityPhotoService.findTopMonthly(target, limit), HttpStatus.OK);
    }
}
