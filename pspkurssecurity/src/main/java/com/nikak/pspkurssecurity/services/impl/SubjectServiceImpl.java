package com.nikak.pspkurssecurity.services.impl;

import com.nikak.pspkurssecurity.dto.SubjectRequest;
import com.nikak.pspkurssecurity.entities.Subject;
import com.nikak.pspkurssecurity.repositories.SubjectRepository;
import com.nikak.pspkurssecurity.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final String FOLDER_PATH = "D:/Desktop/pspk2/subjects/";
    private final SubjectRepository subjectRepository;

    public String createSubject(MultipartFile file, String name) throws IOException{
        boolean subjectIsPresent = subjectRepository.findSubjectByName(name).isPresent();
        if(subjectIsPresent){
            throw new  IllegalStateException("name already taken");
        }
        Subject subjectToSave = new Subject();
        subjectToSave.setName(name);
        Subject savedData;
        if(file != null){
            String filename = UUID.randomUUID().toString() + ".jpg";
            Path saveTO = Paths.get(FOLDER_PATH + filename);

            subjectToSave.setFilename(filename);
            savedData =  subjectRepository.save(subjectToSave);
            Files.copy(file.getInputStream(), saveTO);
        } else savedData =  subjectRepository.save(subjectToSave);

        if(savedData!=null) return "subject : "+name+" added successfully";
        return null;
    }

    public  String updateSubjectName(Long subjectId, String name){
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(()->
                        new IllegalStateException("subject does not exist")
                );
        if(name!=null){
            subject.setName(name);
        }
        Subject saved = subjectRepository.save(subject);
        if(saved!=null) return "successfully updated subject: "+ name;
        return "error";
    }

    public String updateSubjectImage(Long subjectId, MultipartFile file) throws IOException{
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(()->
                        new IllegalStateException("subject does not exist")
                );

        if(file!=null){
            if(subject.getFilename()!=null){

                String filename = subject.getFilename();
                Path saveTO = Paths.get(FOLDER_PATH + filename);
                Files.delete(saveTO);
                Files.copy(file.getInputStream(), saveTO);
            }
            if(subject.getFilename()==null){
                String filename = UUID.randomUUID().toString() + ".jpg";
                Path saveTO = Paths.get(FOLDER_PATH + filename);

                subject.setFilename(filename);
               // savedData =  subjectRepository.save(subjectToSave);
                Files.copy(file.getInputStream(), saveTO);
            }
        }
        Subject savedData =  subjectRepository.save(subject);

        if(savedData!=null) return "subject pic : "+savedData.getFilename()+" updated successfully";
        return null;

    }

    public String deleteSubject(Long subjectId) throws IOException {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(()->
                        new IllegalStateException("subject does not exist")
                );
        if(subject.getFilename()!=null){
            Path deletePath = Paths.get(FOLDER_PATH + subject.getFilename());
            Files.delete(deletePath);
        }
        subjectRepository.deleteById(subjectId);
        return "Subject "+ subject.getName()+ " deleted successfully";
    }

    public List<Object[]> findSubjectsWithCounts(String name){
        if (name == null) name = "";
        return subjectRepository.findSubjectsWithCounts(name);
    }

    public List<Subject> getMostPopularSubjects(PageRequest pageRequest){
        return subjectRepository.findMostPopularSubjects(pageRequest);
    }

    public byte[] getSubjectImage(String filename) throws IOException {
        Optional<Subject> im = subjectRepository.findSubjectByFileName(filename);

        return Files.readAllBytes(
                new File( FOLDER_PATH + im.get().getFilename()).toPath());
    }


    public List<Subject> findAll(){
        return subjectRepository.findAll();
    }


    /*public Subject getSubjectById(Long id){
        return subjectRepository.findById(id).get();
    }

    public Stream<Subject> getAllFiles() {
        return subjectRepository.findAll().stream();
    }

    */
}
