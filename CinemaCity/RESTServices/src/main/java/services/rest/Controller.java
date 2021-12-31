package services.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/aplicatie")
public class Controller {

//    @Autowired
//    private RepoParticipantBD repo_part;
//
//
//    @RequestMapping(value ="/{id}",method = RequestMethod.GET)
//    public NoteJuriu[] getByName(@PathVariable Long id){
//        System.out.println("am primit "+id);
//        List<NoteJuriu> list = repo_part.get_all_note(id);
//        NoteJuriu[] part = list.toArray(new NoteJuriu[list.size()]);
//        return part;
//
//    }
//
//    @RequestMapping(value="/ceva/{id}",method =RequestMethod.GET)
//    public ResponseEntity<?> getById(@PathVariable Long id){
//        Participant part = repo_part.findOne(id);
//        if (part==null){
//            return new ResponseEntity<String>("Cursa not found", HttpStatus.NOT_FOUND);
//        }else
//            return new ResponseEntity<Participant>(part,HttpStatus.OK);
//
//
//    }

}
