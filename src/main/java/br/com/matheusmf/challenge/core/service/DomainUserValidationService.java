package br.com.matheusmf.challenge.core.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.InputMismatchException;
import java.util.Set;

import br.com.matheusmf.challenge.core.domain.User;
import br.com.matheusmf.challenge.core.port.out.UserPersistencePort;
import br.com.matheusmf.challenge.core.service.validation.ValidationResult;
import br.com.matheusmf.challenge.core.service.validation.ValidationStep;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
@Named("domainUserValidationService")
public class DomainUserValidationService implements UserValidationService {

	private final UserPersistencePort userPersistencePort;

	@Override
	public ValidationResult validate(User user) {
		return new CommandConstraintsValidationStep()
				.linkWith(new CpfDuplicationValidationStep(userPersistencePort))
				.linkWith(new CpfValidValidationStep())
				.linkWith(new EmailDuplicationValidationStep(userPersistencePort))
				.linkWith(new BirthdateValidationStep())
				.validate(user);
	}

	private static class CommandConstraintsValidationStep extends ValidationStep<User> {

		@Override
		public ValidationResult validate(User user) {
			try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
				final Validator validator = validatorFactory.getValidator();
				final Set<ConstraintViolation<User>> constraintsViolations = validator.validate(user);

				if (!constraintsViolations.isEmpty()) {
					return ValidationResult.invalid(constraintsViolations.iterator().next().getMessage());
				}
			}
			return checkNext(user);
		}
	}

	private static class CpfDuplicationValidationStep extends ValidationStep<User> {

		private final UserPersistencePort userPersistencePort;

		public CpfDuplicationValidationStep(final UserPersistencePort userPersistencePort) {
			this.userPersistencePort = userPersistencePort;
		}

		@Override
		public ValidationResult validate(User user) {
			if ((user.getId() != null)) {
				if (userPersistencePort.findByCpfAndIdNot(user.getCpf(), user.getId()).isPresent())
					return ValidationResult.invalid(String.format("CPF [%s] already exists", user.getCpf()));
			} else if (userPersistencePort.findByCpf(user.getCpf()).isPresent()) {
				return ValidationResult.invalid(String.format("CPF [%s] already exists", user.getCpf()));
			}
			return checkNext(user);
		}
	}

	private static class CpfValidValidationStep extends ValidationStep<User> {
		@Override
		public ValidationResult validate(User user) {
			if (!isCpf(user.getCpf())) {
				return ValidationResult.invalid(String.format("CPF [%s] is invalid", user.getCpf()));
			}
			return checkNext(user);
		}

		private boolean isCpf(String cpf) {
			if (cpf == null)
				return false;

			if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
					|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
					|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
					|| cpf.equals("99999999999") || (cpf.length() != 11))
				return (false);
			char dig10, dig11;
			int sm, i, r, num, peso;
			try {
				sm = 0;
				peso = 10;
				for (i = 0; i < 9; i++) {
					num = (int) (cpf.charAt(i) - 48);
					sm = sm + (num * peso);
					peso = peso - 1;
				}
				r = 11 - (sm % 11);
				if ((r == 10) || (r == 11))
					dig10 = '0';
				else
					dig10 = (char) (r + 48);
				sm = 0;
				peso = 11;
				for (i = 0; i < 10; i++) {
					num = (int) (cpf.charAt(i) - 48);
					sm = sm + (num * peso);
					peso = peso - 1;
				}
				r = 11 - (sm % 11);
				if ((r == 10) || (r == 11))
					dig11 = '0';
				else
					dig11 = (char) (r + 48);
				if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10)))
					return (true);
				else
					return (false);
			} catch (InputMismatchException erro) {
				return (false);
			}
		}
	}

	private static class EmailDuplicationValidationStep extends ValidationStep<User> {

		private final UserPersistencePort userPersistencePort;

		public EmailDuplicationValidationStep(final UserPersistencePort userPersistencePort) {
			this.userPersistencePort = userPersistencePort;
		}

		@Override
		public ValidationResult validate(User user) {
			
			
			if ((user.getId() != null)) {
				if (userPersistencePort.findByEmailAndIdNot(user.getEmail(), user.getId()).isPresent())
					return ValidationResult.invalid(String.format("Email [%s] is already taken", user.getEmail()));
			} else if (userPersistencePort.findByEmail(user.getEmail()).isPresent()) {
				return ValidationResult.invalid(String.format("Email [%s] is already taken", user.getEmail()));
			}
			return checkNext(user);
		}
	}
	
	private static class BirthdateValidationStep extends ValidationStep<User> {

		@Override
		public ValidationResult validate(User user) {
			int age = Period.between(user.getBirthdate(), LocalDate.now()).getYears();
			if (age < 18) {
				return ValidationResult.invalid("Only users over 18 years old are allowed");
			}
			return checkNext(user);
		}
		
	}

}
