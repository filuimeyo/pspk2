package com.nikak.pspkurssecurity.controllers;


import com.nikak.pspkurssecurity.entities.Purpose;
import com.nikak.pspkurssecurity.entities.Teacher;
import com.nikak.pspkurssecurity.services.PurposeService;
import com.nikak.pspkurssecurity.services.SubjectService;
import com.nikak.pspkurssecurity.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/public/info")
@RequiredArgsConstructor
public class PublicInfoController {

    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final PurposeService purposeService;


    /*@GetMapping("/subject/files")
    public ResponseEntity<List<SubjectResponse>> getListFiles() {
        List<SubjectResponse> files = subjectService.getAllFiles().map(subject -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("api/v1/public/info/subject/files/")
                    .path(String.valueOf(subject.getId()))
                    .toUriString();

            return new SubjectResponse(
                    subject.getId(),
                    subject.getName(),
                    subject.getFilename(),
                    subject.getFilename()!=null? fileDownloadUri : "",
                    subject.getFilename()!=null? subject.getType() : "",
                    subject.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).header("Access-Control-Allow-Origin", "*").body(files);
    }*/

    @GetMapping("/popular")
    public ResponseEntity<?> getListPopularSubjects() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subjectService.getMostPopularSubjects(PageRequest.of(0, 6)));
    }

    @GetMapping("/subjects")
    public ResponseEntity<?> getListSubjects(
            @RequestParam String name
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subjectService.findSubjectsWithCounts(name));
    }

    @GetMapping("/subjects/pic/{filename}")
    public ResponseEntity<?> getSubjectImage(
            @PathVariable String filename
    ) throws IOException {
        byte[] im = subjectService.getSubjectImage(filename);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(im);
    }


   /* @GetMapping("subject/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        Subject subject = subjectService.getSubjectById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Access-Control-Allow-Origin", "*")
                .contentType(MediaType.valueOf(subject.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + subject.getFilename() + "\"")
                .body(subject.getData());
    }*/

    @GetMapping("/teachers/{subjectId}")
    public ResponseEntity<List<Teacher>> getListSubjects(
            @PathVariable Long subjectId,
            @RequestParam Long sort,
            @RequestParam boolean order,
            @RequestParam Long purposeId
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(teacherService.findTeachersBySubjectId(subjectId, purposeId, sort, order));
    }

    @GetMapping("/teachers/one/{teacherId}")
    public ResponseEntity<Optional<Teacher>> getTeacherById(
            @PathVariable Long teacherId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(teacherService.findById(teacherId));
    }

    @GetMapping("/teachers/pic/{filename}")
    public ResponseEntity<?> getTeacherImage(
            @PathVariable String filename
    ) throws IOException {
        byte[] im = teacherService.getTeacherImage(filename);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(im);
    }


    ///certificate
    @GetMapping("/teachers/certificate/{filename}")
    public ResponseEntity<?> getTeacherCertificate(
            @PathVariable String filename
    ) throws IOException {
        byte[] im = teacherService.getCertificateImage(filename);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(im);
    }

    @GetMapping("/purposes")
    public ResponseEntity<List<Purpose>> getPurposes(
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(purposeService.findAll());
    }

    /*@GetMapping("teachers/{subjectId}")
    public ResponseEntity<List<TeacherResponse>> getListSubjects(
            @PathVariable Long subjectId
    ) {


        List<TeacherResponse> files = teacherService.findBySubjectId(subjectId)
                .map(teacher -> new TeacherResponse(
                                teacher.getId(),
                                teacher.getName(),
                                teacher.getInfo(),
                                teacher.getLessonPrice(),
                                teacher.getFilename()!=null? "http://localhost:8080/api/v1/public/info/teachers/one/" + teacher.getId() : "",
                                teacher.getFilename(),
                                teacher.getFilename()!=null? teacher.getType() : "",
                                teacher.getData() != null ? teacher.getData().length : 0
                        )

                ).collect(Collectors.toList());
        System.out.println(files);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Access-Control-Allow-Origin", "*")
                .body(files);
    }*/

   /* @GetMapping("teachers/one/{id}")
    public ResponseEntity<byte[]> getTeacherFile(@PathVariable Long id) {
        Teacher teacher = teacherService.getTeacherById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Access-Control-Allow-Origin", "*")
                .contentType(MediaType.valueOf(teacher.getType()))
               // .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + teacher.getFilename() + "\"")
                .body(teacher.getData());
    }*/
}

