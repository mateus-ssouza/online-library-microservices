package online.library.book.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.library.book.exceptions.ConflictException;
import online.library.book.exceptions.NotFoundException;
import online.library.book.exceptions.RepositoryException;
import online.library.book.models.Copy;
import online.library.book.repositories.CopyRepository;
import online.library.book.services.CopyService;
import online.library.book.utils.Strings;

@Service
public class CopyServiceImpl implements CopyService {

    @Autowired
    private CopyRepository _copyRepository;

    @Override
    public List<Copy> getAll() {
        try {
            return _copyRepository.findAll();
        } catch (Exception e) {
            throw new RepositoryException(Strings.COPY.ERROR_FIND_ALL_LIST, e);
        }
    }

    @Override
    public Optional<Copy> getById(Long id) {
        try {
            Optional<Copy> copy = _copyRepository.findById(id);

            if (!copy.isPresent()) {
                throw new NotFoundException(Strings.COPY.NOT_FOUND);
            }

            return copy;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.COPY.ERROR_FIND_BY_ID, e);
        }
    }

    @Override
    public Copy create(Copy copy) {
        try {
            Optional<Copy> checkCopyCode = _copyRepository.findByCopyCode(copy.getCopyCode());
        

            // Check if there is already a copy with the same copy code
            if (checkCopyCode.isPresent())
                throw new ConflictException(Strings.COPY.CONFLICT);

            return _copyRepository.save(copy);
        } catch (ConflictException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.COPY.ERROR_CREATE, e);
        }
    }

    @Override
    public Copy update(Long id, Copy copy) {
        try {
            Optional<Copy> copyModel = _copyRepository.findById(id);

            // Check if a copy exists by id
            if (!copyModel.isPresent())
                throw new NotFoundException(Strings.COPY.NOT_FOUND);

            Optional<Copy> checkCopyCode = _copyRepository.findByCopyCode(copy.getCopyCode());

            // Check if there is already a copy with the same copy code
            if (checkCopyCode.isPresent() && !checkCopyCode.get().getId().equals(copyModel.get().getId()))
                throw new ConflictException(Strings.COPY.CONFLICT);

            // Updating copy fields
            Copy copyUpdated = copyModel.get();
            copyUpdated.setCopyCode(copy.getCopyCode());
            copyUpdated.setAvailable(copy.isAvailable());

            return _copyRepository.save(copyUpdated);
        } catch (NotFoundException e) {
            throw e;
        } catch (ConflictException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.COPY.ERROR_UPDATE, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (_copyRepository.existsById(id)) {
                // Removing copy by their id
                _copyRepository.deleteById(id);
            } else {
                throw new NotFoundException(Strings.COPY.NOT_FOUND);
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.COPY.ERROR_DELETE, e);
        }
    }
}
