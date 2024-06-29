package musicTranspose.databasereader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@SpringBootApplication
public class DatabaseReaderApplication {
	@Autowired
	private ScoreRepository scoreRepository;

	@Autowired
	private MusicRepository musicRepository;

	@Autowired
	private RegisterRepository registerRepository;
	@Autowired
	private ActiveIdRepository activeIdRepository;

	@GetMapping(value = "/music", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Music>> getMusic() {
		try {
			List<Music> musicList = (List<Music>) musicRepository.findAll();
			return ResponseEntity.ok(musicList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(new ArrayList());
		}
	}

	@GetMapping(value = "/registers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Register>> getRegisters() {
		try {
			List<Register> registerList = (List<Register>) registerRepository.findAll();
			return ResponseEntity.ok(registerList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(new ArrayList());
		}

	}

	@GetMapping(value = "/score/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> getScoreById(@PathVariable Integer id) {
		Optional<Score> scoreOptional = scoreRepository.findById(id);
		if (scoreOptional.isPresent()) {
			Score score = scoreOptional.get();
			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_PDF)
					.body(score.getS_data());
		} else {
			return ResponseEntity.ok().build();
		}
	}

	@GetMapping(value = "/active-scores", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ScoreWithoutData>> getActiveScores() {
		Optional<ActiveId> activeIdOptional = activeIdRepository.findLatestEntry();
		if (activeIdOptional.isPresent()) {
			List<ScoreWithoutData> scoreList = scoreRepository.findByMusicIdWithoutData(activeIdOptional.get().getId());
			if (!scoreList.isEmpty()) {
				return ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(scoreList);
			} else {
				return ResponseEntity.ok().body(new ArrayList());
			}
		} else {
			return ResponseEntity.ok().body(new ArrayList());
		}
	}
	public static void main(String[] args) {
		SpringApplication.run(DatabaseReaderApplication.class, args);
	}

}
