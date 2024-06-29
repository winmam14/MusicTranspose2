package musicTranspose.databasewriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@SpringBootApplication
public class DatabaseWriterApplication {
	@Autowired
	private ActiveIdRepository activeIdRepository;
	@Autowired
	private ScoreRepository scoreRepository;
	@Autowired
	private RegisterRepository registerRepository;
	@Autowired
	private MusicRepository musicRepository;


	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> addNewScores(@RequestPart("metadata") List<ScoreMetadata> metadataList,
											   @RequestPart("document") List<MultipartFile> pdfFiles) {
		try {
			if (metadataList.size() != pdfFiles.size()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Metadata count does not match PDF files count");
			}

			for (int i = 0; i < metadataList.size(); i++) {
				ScoreMetadata metadata = metadataList.get(i);
				MultipartFile pdfFile = pdfFiles.get(i);

				// Create a new Score object
				Score score = new Score();

				// Retrieve Register entity from the database
				Register register = registerRepository.findById(metadata.getRegister_id())
						.orElse(null);
				if (register == null) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body("Invalid register ID: " + metadata.getRegister_id());
				}

				// Retrieve Music entity from the database
				Music music = musicRepository.findById(metadata.getMusic_id())
						.orElse(null);
				if (music == null) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body("Invalid music ID: " + metadata.getMusic_id());
				}

				// Set metadata
				score.setS_date_added(new Date(metadata.getDateAdded() * 1000)); // Convert Unix timestamp to milliseconds
				score.setS_part(metadata.getPart());
				score.setS_description(metadata.getDescription());

				// Set register and music
				score.setRegister(register);
				score.setMusic(music);

				// Set PDF file data
				score.setS_data(pdfFile.getBytes());

				// Save the Score object to the database
				scoreRepository.save(score);
			}

			return ResponseEntity.ok("Saved successfully");
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Save");
		}
	}

	@PostMapping(value = "/active/{id}")
	public @ResponseBody ResponseEntity<String> setActiveId(@PathVariable Integer id) {
		try {
			Integer primaryKey = 1;

			// Retrieve the first entry by primary key
			Optional<ActiveId> existingEntry = activeIdRepository.findById(primaryKey);

			ActiveId activeIdEntry;
			if (existingEntry.isPresent()) {
				// If the entry exists, update it
				activeIdEntry = existingEntry.get();
			} else {
				// If the entry does not exist, create a new one with the primary key 1
				activeIdEntry = new ActiveId();
				activeIdEntry.setIDNr(primaryKey);
			}

			// Set the new ID value
			activeIdEntry.setId(id);

			// Save the updated entry
			activeIdRepository.save(activeIdEntry);
			return ResponseEntity.ok("Saved successfully: " + id);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Save");
		}
	}

	@PostMapping(value = "/music")
	public @ResponseBody ResponseEntity<String> addMusic(@RequestParam String name) {
		try {
			Music newMusic = new Music();
			newMusic.setM_name(name);

			musicRepository.save(newMusic);
			return ResponseEntity.ok("Music added with name: " + name);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Save");
		}
	}

	@PostMapping(value = "/register")
	public @ResponseBody ResponseEntity<String> addRegister(@RequestParam String name) {
		try {
			Register newRegister = new Register();
			newRegister.setR_name(name);

			registerRepository.save(newRegister);
			return ResponseEntity.ok("Register added with name: " + name);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Save");
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(DatabaseWriterApplication.class, args);
	}

}
