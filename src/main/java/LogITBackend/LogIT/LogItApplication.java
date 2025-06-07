package LogITBackend.LogIT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LogItApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogItApplication.class, args);
		// hihi
//		// 1. NullPointereption
//		str.length(); // 런타임 예외 → throws 불필요
//
//		// 2. ArrayIndexOutOfBoundsException
//		int[] arr = new int[3];
//		int value = arr[5]; // 런타임 예외
//
//		// 3. ArithmeticException
//		int result = 10 / 0; // 런타임 예외
	}

}
