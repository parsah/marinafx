'''
@author: Parsa Hosseini
'''

import argparse
import collections
import copy

'''
High-level class which oversees merging of various coordinates-files into a 
singular matrix data-structure.
@author: Parsa Hosseini
'''
class MergerFactory():
    def __init__(self, files, out):
        self.files = files # list / collection of input filenames
        self.out = out # output filename
        self.unstruct_counts = [] # contains counts but is not in matrix form
        self.count_skeleton = None # initially, no matrix is built
        
    def parse_files(self):
        ''' 
        Parses all the user-provided TFBS coordinates files and stores them in
        a way where they can be easily indexed.
        '''
        try:
            for f in self.files:
                file_handle = open(f)
                for line in file_handle:
                    line = line.strip().split('\t')
                    tfbs_count = 0
                    group, seq_name, tfbs = line[0:3]
                    if len(line) != 3: # first 3 columns are descriptors
                        tfbs_count = len(line[3:]) # thereof is coordinates
                    # save TFBS-specific abundances as a dictionary
                    abundance = {'group': group, 'name': seq_name, 'tfbs':tfbs, 
                           'num':tfbs_count}
                    self.unstruct_counts.append(abundance)
        except IOError:
            print('Invalid TFBS coordinate file [error]')
        except (IndexError, ValueError):
            print('Please check if input is TFBS coordinate file [error]')
            
    def get_rows(self):
        ''' 
        Generates a singular collection of only unique group names and 
        sequence names. This is because each TFBS Coordinates file has a line
        for every TFBS, therefore if many TFBSs hit that sequence, there will
        be many repetitions of that sequence name. This behavior represents
        these repetitions and only displays each name just once.
        
        @return: rows - unique collection of group and sequence names. 
        '''
        rows = collections.OrderedDict()
        for i in self.unstruct_counts:
            rows[i['group'] + '\t' + i['name']] = None
        return rows
    
    def bare_columns(self):
        ''' 
        Creates a centralized collection of all the TFBSs found in all the
        TFBS Coordinate files. This data-structure is essentially a dictionary
        because each TFBS and its initial zero-count is the key and value
        respectively. Each sequence in the matrix will have its own unique 
        copy of this data-structure, providing the ability for the sequence to
        easily store and keep-track of sequence-specific TFBS abundance.
        
        @return: columns - key/value of TFBS name and a zero-count abundance.
        '''
        columns = collections.OrderedDict()
        for i in self.unstruct_counts:
            tfbs = i['tfbs']
            if tfbs not in columns: # if TFBS not in map, add it
                columns[tfbs] = 0 # give it a unique integer ID
        return columns
        
    def build_count_skeleton(self):
        ''' 
        Creates a bare-bones data-structure whereby each row references all 
        the TFBSs and their initial zero-count. This data-structure provides
        the ability to easily identify TFBSs abundant (or otherwise) within a
        specific input sequence.
        '''
        cols = self.bare_columns()
        rows = self.get_rows()
        for key in rows:
            rows[key] = copy.deepcopy(cols)
        self.count_skeleton = rows
        
    def populate(self):
        ''' 
        Iteratively sets TFBSs abundances to each respective input sequence.
        '''
        
        # iterate through all the TFBS abundances and populate the
        # bare-bones skeleton matrix with them.    
        try:
            for i in self.unstruct_counts:
                key = i['group'] + '\t' + i['name']
                tfbs, num = i['tfbs'], i['num']
                self.count_skeleton[key][tfbs] = num # set count
        except TypeError: # bare-bones skeleton must be made first
            print('Must create count-skeleton first [error]')
    
    def write(self):
        ''' 
        Saves a count-skeleton data-structure to file.
        '''
        writer = open(self.out, 'w')
        header = ['Group','Sequence'] + list(self.bare_columns().keys())
        writer.write('\t'.join(header) + '\n')
        writer.flush()
        for i in self.count_skeleton:
            # get TFBS counts for each respective input sequence
            tfbs_counts = list(self.count_skeleton[i].values())
            tfbs_counts = [str(i) for i in tfbs_counts]
            writer.write(i + '\t' + '\t'.join(tfbs_counts) + '\n')
            writer.flush()
        writer.close()
        print('Operation complete [ok]')
            
if __name__ == '__main__':
    desc = ''' 
    Upon running Marina, you have the option of saving "TFBS Coordinates" 
    within the File menu. The resultant file contains exact indices for where 
    each TFBSs mapped onto various user-provided input sequences. Each time 
    Marina is ran, one "TFBS Coordinates" file can be saved. This script 
    enables such files to be "massaged" into a matrix. Each cell in the matrix 
    represents the abundance of the TFBS in the respective user-provided 
    sequence. Such an abundance matrix could therefore be used as input in 
    classification tasks such as regression or SVM tasks.
    '''
    
    parser = argparse.ArgumentParser(description = desc, add_help = False)
    param_reqd = parser.add_argument_group('Required')
    param_opts = parser.add_argument_group('Optional')
    param_reqd.add_argument('-in', required=True, metavar='', nargs='+',
                            help='One or more Marina-saved "TFBS Coordinate" files [na]')
    param_reqd.add_argument('-out', metavar='', type=str, default='out_matrix.tab',
                            help='Matrix output filename [out_matrix.tab]')
    param_opts.add_argument('-h','--help', action='help', 
                            help='Show this help screen and exit')
    args = vars(parser.parse_args())
    factory = MergerFactory(files = args['in'], out=args['out'])
    factory.parse_files()
    factory.build_count_skeleton()
    factory.populate()
    factory.write()